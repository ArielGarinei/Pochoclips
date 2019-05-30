package com.example.user.pochoclips.views.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.bumptech.glide.Glide;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.controllers.ControladorFavoritos;
import com.example.user.pochoclips.controllers.ControladorFavoritosRoom;
import com.example.user.pochoclips.controllers.ControladorImagen;
import com.example.user.pochoclips.controllers.ControladorMovie;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Imagen;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.POJO.Recomendacion;
import com.example.user.pochoclips.views.activities.ActivityLogin;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterImagenes;
import com.example.user.pochoclips.views.fragments.recyclerView.RecyclerviewFragmentSeleccionarUsuario;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentDetalleActores;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.storage.FirebaseStorage;
import com.varunest.sparkbutton.SparkButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetalle extends Fragment implements RecyclerviewFragmentSeleccionarUsuario.EscuchadorDelFragmentSeleccionarUsuario{

    private ImageView imageViewImagenPelicula;
    private TextView textViewNombrePelicula;
    private TextView textViewReleaseDate;
    private TextView textViewDescripcionPelicula_Detalle;
    private ImageButton imageButtonRecomendar;
    private ImageButton imageButtonCompartir;
    private ImageButton imageButtonFavorito;
    private FloatingActionButton fabVerVideo;
    private MaterialRatingBar ratingBar;
    private SparkButton sparkButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuarioActual;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceRating;
    private FirebaseStorage firebaseStorage;
    private Activity activity;
    private RecyclerView recyclerViewImagenesDePelicula;
    private CardView cardViewRecyclerImagenes;
    private TextView textViewPopularidadPelicula;
    private Double averageMovie;



    public static FragmentDetalle crearDetalleFragment(Movie movie) {
        //esto es lo que se usa en el viewPagerAdapter para crear el fragmento apartir de la posicion en la lista.
        FragmentDetalle fragmentDetalle = new FragmentDetalle();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TMDBHelper.SERIALIZABLE, movie);
        fragmentDetalle.setArguments(bundle);
        return fragmentDetalle;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        usuarioActual = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Bundle bundle = getArguments();
        RecyclerViewFragmentDetalleActores recyclerViewFragmentDetalleActores = new RecyclerViewFragmentDetalleActores();
        getChildFragmentManager().beginTransaction().replace(R.id.contenedorRecyclerActores, recyclerViewFragmentDetalleActores).commit();
        recyclerViewFragmentDetalleActores.setArguments(bundle);

        cardViewRecyclerImagenes = view.findViewById(R.id.cardViewRecyclerImagenes);
        recyclerViewImagenesDePelicula = view.findViewById(R.id.recyclerViewImagenesPelicula);
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViewImagenesDePelicula.setLayoutManager(layoutManager);
        recyclerViewImagenesDePelicula.setHasFixedSize(false);

        if (estaLogueado()) {
            databaseReferenceRating = firebaseDatabase.getReference("User").child(usuarioActual.getUid()).child("ratings").child(filtrarTitulo(obtenerMovieDelBundle().getTitle()) + "  ID: " + obtenerMovieDelBundle().getId().toString());
            databaseReferenceRating.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Long ratingEnLong = dataSnapshot.getValue(Long.class);
                    if (ratingEnLong != null) {
                        ratingBar.setRating(ratingEnLong);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
        // textViewDirector.setText(resultado.get(posicionSeleccionada).getDirector());
        imageViewImagenPelicula = view.findViewById(R.id.imageViewImagenPelicula);
        textViewNombrePelicula = view.findViewById(R.id.textViewNombrePelicula);
        textViewReleaseDate = view.findViewById(R.id.textViewReleaseDate);
        textViewPopularidadPelicula = view.findViewById(R.id.textViewPuntajeTotal);
        ratingBar = view.findViewById(R.id.ratingBar_FragmentDetalle);
        ratingBar.setMax(5);
        //textViewDirector = view.findViewById(R.id.textViewDirector_Detalle);
        textViewDescripcionPelicula_Detalle = view.findViewById(R.id.textVewDescripcion);
        imageButtonCompartir = view.findViewById(R.id.imageButtonCompartir);
        imageButtonRecomendar = view.findViewById(R.id.imageButtonRecomendar);
        imageButtonRecomendar = view.findViewById(R.id.imageButtonRecomendar);
        sparkButton = view.findViewById(R.id.heart_button);
        averageMovie = (obtenerMovieDelBundle().getVoteAverage() / 2);
        textViewPopularidadPelicula.setText(averageMovie.toString());

        fabVerVideo = view.findViewById(R.id.fabVerVideo);

        setearDatosDePelicula();
        setearImagenesDePelicula();

        imageButtonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearYCompartirShortDynamicLink();
            }
        });

        if (preguntarSiHayFavoritos()){
            ((SparkButton) view.findViewById(R.id.heart_button)).setChecked(true);
        }else{
            ((SparkButton) view.findViewById(R.id.heart_button)).setChecked(false);
        }

        sparkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!estaLogueado()) {
                    Toast.makeText(getContext(), "Logeate para agregar Favoritos", Toast.LENGTH_SHORT).show();
                } else if(estaLogueado()){
                    Movie pelicula = obtenerMovieDelBundle();
                    ControladorFavoritos controladorFavoritos = new ControladorFavoritos(getContext(), usuarioActual.getUid(), pelicula);
                    ControladorFavoritos controladorFavoritosFirebase = new ControladorFavoritos(getContext(), usuarioActual.getUid(), pelicula);
                    if (pelicula.getFavorito() == 0) {
                        controladorFavoritos.guardarFavoritos();
                        pelicula.setFavorito(1);
                        playHeartAnimationTrue(view);
                    } else if(pelicula.getFavorito() == 1){
                        controladorFavoritos.borrarFavoritos();
                        controladorFavoritosFirebase.borrarFavoritosFirebase();
                        pelicula.setFavorito(0);
                        playHeartAnimationFalse(view);
                    }
                }
            }
        });

        imageButtonRecomendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getChildFragmentManager();
                RecyclerviewFragmentSeleccionarUsuario recyclerviewFragmentSeleccionarUsuario = new RecyclerviewFragmentSeleccionarUsuario();
                recyclerviewFragmentSeleccionarUsuario.show(fragmentManager,"AMIGOS");
            }
        });

        fabVerVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleParaYoutube = new Bundle();
                bundleParaYoutube.putInt(FragmentYoutube.KEY_YOUTUBE, obtenerMovieDelBundle().getId());
                FragmentYoutube fragmentYoutube = new FragmentYoutube();
                fragmentYoutube.setArguments(bundleParaYoutube);
                FragmentManager fragmentManagerYoutube = getFragmentManager();
                fragmentYoutube.show(fragmentManagerYoutube,"fragmentYoutube");
            }
        });

        ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                if (estaLogueado()) {
                    databaseReferenceRating.setValue(rating);
                } else {
                    ratingBar.setRating(0);
                    Toast.makeText(getContext(), "Inicia Sesion para poder Puntuar", Toast.LENGTH_LONG).show();
                    intentAlAcltivityLogin();

                }
            }
        });
        return view;
    }

    private void setearImagenesDePelicula() {
        ControladorImagen controladorImagen = new ControladorImagen(getContext());
        controladorImagen.obtenerImagenesDePelicula(new ResultListener<List<Imagen>>() {
            @Override
            public void finish(List<Imagen> resultado) {
                if (resultado.size() != 0){
                    cardViewRecyclerImagenes.setVisibility(View.VISIBLE);
                    RecyclerViewAdapterImagenes recyclerViewAdapterImagenes = new RecyclerViewAdapterImagenes(resultado, getContext());
                    recyclerViewImagenesDePelicula.setAdapter(recyclerViewAdapterImagenes);
                }
            }
        }, obtenerMovieDelBundle().getId());


    }

    public String filtrarTitulo(String titulo) {

        String tituloFiltrado = null;
        if (titulo.contains(".")) {
            tituloFiltrado = titulo.replace(".", " ");
        }
        return tituloFiltrado;
    }


    public void setearDatosDePelicula(){

        String titulo = obtenerMovieDelBundle().getTitle();
        String detalle = obtenerMovieDelBundle().getOverview();
        String releaseDate = obtenerMovieDelBundle().getReleaseDate();

        textViewNombrePelicula.setText(titulo);

        if (detalle != null){
            textViewDescripcionPelicula_Detalle.setText(detalle);
        }

        if (releaseDate != null){
            textViewReleaseDate.setText(releaseDate);
        }

        Glide.with(getContext()).load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W1280, obtenerMovieDelBundle().getBackdropPath())).into(imageViewImagenPelicula);
    }

    private void intentAlAcltivityLogin() {
        Intent intent = new Intent(getContext(), ActivityLogin.class);
        startActivity(intent);
    }

    @Override
    public void seleccionaronAlUsuario2(String userId) {
        Movie movie = obtenerMovieDelBundle();
        Random random = new Random();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference()
                .child("User")
                .child(userId)
                .child("Recomendados")
                .child(movie.getTitle());
        //TIENE QUE SELEECIONAR A QUIEN ENVIAR LA RECOMENDACION
        databaseReference.setValue(new Recomendacion(random.toString(),
                usuarioActual.getDisplayName(),usuarioActual.getUid(),usuarioActual.getPhotoUrl().toString(),
                movie.getId(),movie.getBackdropPath(),movie.getOriginalTitle(),
                movie.getOverview(),movie.getPopularity(),movie.getPosterPath(),
                movie.getReleaseDate(),movie.getTitle(),movie.getVoteAverage()));

    }

    private boolean estaLogueado() {
        return (usuarioActual != null);
    }

    public Uri crearDynamicLink(){

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(TMDBHelper.getBaseUrl() + "movie/" + obtenerMovieDelBundle().getId() + "?api_key=" + TMDBHelper.getApiKey() + "&language=" + TMDBHelper.language_ENGLISH))
                .setDynamicLinkDomain("pochoclips.page.link")
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        return dynamicLinkUri;
    }

    public void compartirDynamicLink(Uri uriACompartir){

        Intent sendIntent = new Intent();
        String mensajeACompartir = "¡Hola! Te recomiendo esta pelicula: " + uriACompartir.toString();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mensajeACompartir);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void crearYCompartirShortDynamicLink(){
        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse(crearDynamicLink().toString()))
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("pochoclips.page.link")
                                .setFallbackUrl(Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
                                .build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("pochoclips.page.link")
                                .setFallbackUrl(Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(activity ,new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            Uri shortLink = task.getResult().getShortLink();
                            compartirDynamicLink(shortLink);

                        } else {
                            Toast.makeText(getContext(), "Error creando short dynamic link", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void firebaseEsIgualCero(){
        ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(getContext());
        controladorFavoritosRoom.addFavoritos(obtenerMovieDelBundle().getId());

        DatabaseReference databaseReference;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        DatabaseReference reference = databaseReference.child("User")
                .child(usuarioActual.getUid())
                .child("MisFavoritos")
                .child(obtenerMovieDelBundle().getTitle());
        reference.setValue(obtenerMovieDelBundle().getId());
    }

    public void firebaseMayorCero(){
        //Add Pelicula a Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference reference = databaseReference.child("User")
                .child(usuarioActual.getUid())
                .child("MisFavoritos")
                .child(obtenerMovieDelBundle().getTitle());
        reference.setValue(obtenerMovieDelBundle().getId());


        //Obtengo Toda La Lista De Firebase
        DatabaseReference reference2 = databaseReference.child("User")
                .child(usuarioActual.getUid())
                .child("MisFavoritos");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(getContext());
                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()){
                    Integer integer= dataSnapshotChild.getValue(Integer.class);
                    controladorFavoritosRoom.addFavoritos(integer);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        reference2.addValueEventListener(valueEventListener);

    }

    public List<Integer> pedidoFirebase(){
        DatabaseReference databaseReference;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final List<Integer> listaFirebase = new ArrayList<>();
        DatabaseReference reference2 = databaseReference.child("User")
                .child(usuarioActual.getUid())
                .child("MisFavoritos");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()){
                    Integer integer= dataSnapshotChild.getValue(Integer.class);
                    listaFirebase.add(integer);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        reference2.addValueEventListener(valueEventListener);
        return listaFirebase;
    }

    public Movie obtenerMovieDelBundle(){
        Bundle bundle = getArguments();
        Movie pelicula = (Movie) bundle.getSerializable(TMDBHelper.SERIALIZABLE);
        return pelicula;
    }
    public List<Recomendacion> obtenerRecomendacionDelBundle(){
        Bundle bundle = getArguments();
        List<Recomendacion> recomendacion = (List<Recomendacion>) bundle.getSerializable("recomendaciones");
        return recomendacion;
    }

    private void playHeartAnimationTrue(final View heartLayout) {
        ((SparkButton) heartLayout.findViewById(R.id.heart_button)).setChecked(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((SparkButton) heartLayout.findViewById(R.id.heart_button)).setChecked(true);
                ((SparkButton) heartLayout.findViewById(R.id.heart_button)).playAnimation();
            }
        }, 300);
    }

    private void playHeartAnimationFalse(final View heartLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((SparkButton) heartLayout.findViewById(R.id.heart_button)).playAnimation();
                ((SparkButton) heartLayout.findViewById(R.id.heart_button)).setChecked(false);
            }
        }, 300);
    }

    private Boolean preguntarSiHayFavoritos() {
        Movie pelicula = obtenerMovieDelBundle();
        ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(getContext());
        List<Movie> todasLasFavoritas = controladorFavoritosRoom.getFavoritos();
        for (Movie cadaFavorita : todasLasFavoritas) {
            if(pelicula.getId().equals(cadaFavorita.getId()) && cadaFavorita.getFavorito() == 1){
                return true;
            }
        }
        return false;
    }
    private List<Movie>  preguntarSiHayRecomendados() {
        List<Recomendacion> recomendacionList = obtenerRecomendacionDelBundle();
        List<Movie> movieList = new ArrayList<>();
        for (Recomendacion recomendacion : recomendacionList) {
            Movie movie = new Movie(recomendacion.getId(),recomendacion.getBackdropPath(),recomendacion.getOriginalTitle(),recomendacion.getOverview(),recomendacion.getPopularity(),recomendacion.getPosterPath(),recomendacion.getReleaseDate(),recomendacion.getTitle(),recomendacion.getVoteAverage(),0);
            movieList.add(movie);
        }

        return movieList;
    }

}

