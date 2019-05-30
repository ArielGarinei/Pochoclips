package com.example.user.pochoclips.views.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.controllers.ControladorFavoritos;
import com.example.user.pochoclips.controllers.ControladorFavoritosRoom;
import com.example.user.pochoclips.controllers.ControladorMovieRoom;
import com.example.user.pochoclips.models.POJO.Favorito;
import com.example.user.pochoclips.models.POJO.Filtro;
import com.example.user.pochoclips.models.POJO.Recomendacion;
import com.example.user.pochoclips.views.fragments.FragmentPeliculasFiltradas;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentFiltro;
import com.example.user.pochoclips.views.fragments.FragmentTAB;
import com.example.user.pochoclips.models.POJO.Movie;

import com.example.user.pochoclips.views.fragments.FragmentPerfil;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentFavoritos;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentInicio;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentRecomendados;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityInicio extends AppCompatActivity implements RecyclerViewFragmentInicio.NotificadorDeActivities, RecyclerViewFragmentFavoritos.NotificadorDeActivitiesFab,RecyclerViewFragmentRecomendados.NotificadorDeRecomendaciones, RecyclerViewFragmentFiltro.NotificadorDeFiltroEnActivity, FragmentPeliculasFiltradas.NotificadorDePeliculaFiltradaDetalle {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser usuarioActual;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private ImageView imageViewFotoUsuarioNavigationView;
    private TextView textViewNombreUsuarioNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        firebaseAuth = FirebaseAuth.getInstance();
        usuarioActual = firebaseAuth.getCurrentUser();

        //---NavigationView---
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView_InicioActivity);
        navigationView.setNavigationItemSelectedListener(new ListenerMenu());

        MenuItem itemCerrarSesion = navigationView.getMenu().findItem(R.id.itemCerrarSesion);
        itemCerrarSesion.setVisible(!(usuarioActual == null));

        //------------------ HEADER ----------------------//
         View viewHeader = navigationView.getHeaderView(0);
        textViewNombreUsuarioNavigationView = viewHeader.findViewById(R.id.textViewNombreUsuario_NavigationViewHeader);
        imageViewFotoUsuarioNavigationView = viewHeader.findViewById(R.id.imageViewFotoUsuario_NavigationViewHeader);
        if (usuarioActual != null) {
            textViewNombreUsuarioNavigationView.setText(usuarioActual.getDisplayName());
            Glide.with(ActivityInicio.this).load(usuarioActual.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imageViewFotoUsuarioNavigationView);
        }
        //------ AppBar ------//
        Toolbar myToolbar = findViewById(R.id.inicioToolBar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //-----------------  Cargar Fragment ---------------------//

        cargarFragmentPeliculas();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) return true;

        switch (item.getItemId()) {
            case R.id.filtrarGenero:
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecyclerViewFragmentFiltro recyclerViewFragmentFiltro = new RecyclerViewFragmentFiltro();
                recyclerViewFragmentFiltro.show(fragmentManager,"FILTROS");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void seleccionaronLaPelicula(int posicion, List<Movie> movieList) {
        enviarBundleADetalle(posicion, movieList);
    }

    @Override
    public void seleccionaronLaPeliculaFab(int posicion, List<Movie> movieList) {
        enviarBundleADetalle(posicion, movieList);
    }

    public void cargarFragmentPeliculas() {
//        ControladorFavoritosRoom controladorFavoritosRoom = new ControladorFavoritosRoom(this);
//        List<Movie> movies = controladorFavoritosRoom.getFavoritos();
//
//        Toast.makeText(this, movies.toString(), Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentTAB fragmentTAB = new FragmentTAB();
        fragmentTransaction.replace(R.id.contenedor_InicioActivity, fragmentTAB);
        fragmentTransaction.commit();
    }

    public void enviarBundleADetalle(int posicion, List<Movie> movieList) {
        Intent intent = new Intent(ActivityInicio.this, ActivityDetalle.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TMDBHelper.POSICION, posicion);
        bundle.putSerializable(TMDBHelper.SERIALIZABLE, (Serializable) movieList);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void enviarBundleADetalleRecomendacion(int posicion, List<Recomendacion> recomendacionList) {
        List<Movie> movieList = new ArrayList<>();
        for (Recomendacion recomendacion : recomendacionList) {
            Movie movie = new Movie(recomendacion.getId(),recomendacion.getBackdropPath(),recomendacion.getOriginalTitle(),recomendacion.getOverview(),recomendacion.getPopularity(),recomendacion.getPosterPath(),recomendacion.getReleaseDate(),recomendacion.getTitle(),recomendacion.getVoteAverage(),0);
            movieList.add(movie);
        }
        Intent intent = new Intent(ActivityInicio.this, ActivityDetalle.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TMDBHelper.POSICION, posicion);
        bundle.putSerializable(TMDBHelper.SERIALIZABLE, (Serializable) movieList);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void seleccionaronLaRecomendacion(int posicionRecomendacion, List<Recomendacion> recomendacionList) {
       enviarBundleADetalleRecomendacion(posicionRecomendacion,recomendacionList);
        Toast.makeText(this, "SE SE LECCIONO LA RECOMENDACION", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void filtroSeleccionadoEnActivity(Filtro filtro) {
        FragmentPeliculasFiltradas fragmentPeliculasFiltradas = new FragmentPeliculasFiltradas();

        String nombreGeneroPocho = filtro.getNombreGeneroPocho();
        String idGenero = filtro.getIdGenero();
        Integer imagenGeneroPocho = filtro.getImagenGeneroPocho();
        Bundle bundle = new Bundle();
        bundle.putString(FragmentPeliculasFiltradas.NOMBRE_GENERO,nombreGeneroPocho);
        bundle.putString(FragmentPeliculasFiltradas.ID_GENERO,idGenero);
        bundle.putInt(FragmentPeliculasFiltradas.IMAGEN_GENERO,imagenGeneroPocho);
        fragmentPeliculasFiltradas.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_InicioActivity,fragmentPeliculasFiltradas)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cargarDetalleDePeliculaFiltrada(Integer posicion, List<Movie> listaPeliculasFiltradas) {
        enviarBundleADetalle(posicion,listaPeliculasFiltradas);
    }

    //La siguiente clase es el Listener para el Menu del Navigation View
    private class ListenerMenu implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectedMenuItem(item);
            return true;
        }
    }

    //El siguiente Metodo representa lo que hara el Menu dependiendo del Item seleccionado
    private void selectedMenuItem(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.itemInicio:
                cargarFragmentPeliculas();
                break;

            case R.id.itemUsuario:
                if (usuarioActual != null) {
                    FragmentPerfil fragmentPerfil = new FragmentPerfil();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.contenedor_InicioActivity, fragmentPerfil);
                    fragmentTransaction.commit();
                } else {
                    Intent intent = new Intent(ActivityInicio.this, ActivityLogin.class);
                    startActivity(intent);
                }
                break;

            /*case R.id.itemConfiguracion:
                //TODO este item debe llevar al usuario a la Configuracion
                Toast.makeText(this, "Configuracion", Toast.LENGTH_SHORT).show();
                break;*/

            case R.id.itemAcercaDePochoclips:
                //TODO este item debe llevar al usuario a la pantalla que detalle la info de nuestra aplicacion ("AboutUs")
                Toast.makeText(this, "Acerca De Pochoclips", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemCerrarSesion:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                //GoogleApiManager.reportSignOut(); //TODO: Investigar esto
                Intent intent = new Intent(ActivityInicio.this, ActivityLogin.class);
                startActivity(intent);
        }

        drawerLayout.closeDrawers();
    }

}
