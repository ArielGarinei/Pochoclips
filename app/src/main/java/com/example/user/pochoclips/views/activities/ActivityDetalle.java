package com.example.user.pochoclips.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import com.example.user.pochoclips.controllers.ControladorMovie;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.DAO.DAOMovieInternet;
import com.example.user.pochoclips.models.POJO.Cast;
import com.example.user.pochoclips.models.POJO.Movie;
import com.example.user.pochoclips.models.POJO.Recomendacion;
import com.example.user.pochoclips.views.adapter.RecyclerViewAdapterSeleccionarUsuario;
import com.example.user.pochoclips.views.adapter.ViewPagerAdapterDetalle;
import com.example.user.pochoclips.views.fragments.FragmentPerfil;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentDetalleActores;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentFiltro;
import com.example.user.pochoclips.views.fragments.view_pager.ViewPageFragmentDetalle;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityDetalle extends AppCompatActivity implements
        RecyclerViewFragmentDetalleActores.NotificadorDeActivitiesActor {

    private ViewPager viewPager;
    private ViewPagerAdapterDetalle viewPagerAdapterDetalle;
    private FirebaseUser usuarioActual;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Uri deepLink;
    private Bundle bundle;
    private Intent intent;
    private ImageView imageViewFotoUsuarioNavigationView;
    private TextView textViewNombreUsuarioNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

        intent = getIntent();
        bundle = intent.getExtras();

        //---NavigationView---
        drawerLayout = findViewById(R.id.contenedor_DetalleActivity);
        navigationView = findViewById(R.id.navigationView_DetalleActivity);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new EscuchadorMenuNavigationView());
        MenuItem itemCerrarSesion = navigationView.getMenu().findItem(R.id.itemCerrarSesion);
        if(usuarioActual == null) itemCerrarSesion.setVisible(false);
        else itemCerrarSesion.setVisible(true);

        View viewHeader = navigationView.getHeaderView(0);

        textViewNombreUsuarioNavigationView = viewHeader.findViewById(R.id.textViewNombreUsuario_NavigationViewHeader);
        imageViewFotoUsuarioNavigationView = viewHeader.findViewById(R.id.imageViewFotoUsuario_NavigationViewHeader);

        if (usuarioActual != null) {
            textViewNombreUsuarioNavigationView.setText(usuarioActual.getDisplayName());
            Glide.with(ActivityDetalle.this).load(usuarioActual.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imageViewFotoUsuarioNavigationView);
        }

        //-------AppBar-------
        Toolbar myToolbar = (Toolbar) findViewById(R.id.inicioToolBar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewPager);



        if (intentVieneDeDynamicLink()){
            cargarFragmentDetalleDesdeDynamicLink();
        } else {
            cargarFragmentDetalle();
        }

    }

    public boolean intentVieneDeDynamicLink(){
        //Para comprobar si el intent viene del dynamic link, me fijo si su bundle contiene una posicion
        return !intent.hasExtra(TMDBHelper.POSICION);
    }

    public void cargarFragmentDetalle(){
        List<Movie> movieList = (List<Movie>) bundle.getSerializable(TMDBHelper.SERIALIZABLE);
        Integer posicion = bundle.getInt(TMDBHelper.POSICION);

        viewPagerAdapterDetalle = new ViewPagerAdapterDetalle(getSupportFragmentManager(),movieList);
        viewPager.setAdapter(viewPagerAdapterDetalle);
        viewPager.setCurrentItem(posicion);
    }


    public void cargarFragmentDetalleDesdeDynamicLink(){

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(ActivityDetalle.this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();

                            String linkSinBaseURL = deepLink.toString().replace(TMDBHelper.getBaseUrl() + "movie/", "");
                            String linkSinBaseURLNiQuery = linkSinBaseURL.substring(0, linkSinBaseURL.indexOf('?'));
                            final Integer movieID = Integer.valueOf(linkSinBaseURLNiQuery);

                            ControladorMovie controladorMovie = new ControladorMovie(ActivityDetalle.this);
                            controladorMovie.ObtenerMoviewDetail(new ResultListener<Movie>() {
                                @Override
                                public void finish(Movie resultado) {

                                    List<Movie> movieList = new ArrayList<>();
                                    movieList.add(resultado);

                                    viewPagerAdapterDetalle = new ViewPagerAdapterDetalle(getSupportFragmentManager(),movieList);
                                    viewPager.setAdapter(viewPagerAdapterDetalle);
                                    viewPager.setCurrentItem(0);
                                }
                            }, movieID);
                        }
                    }
                })
                .addOnFailureListener(ActivityDetalle.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityDetalle.this, "SE ROMPIO TODO EL DYNAMIC LINK" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        MenuItem item = menu.findItem(R.id.filtrarGenero);
        item.setVisible(false);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
        /*switch (item.getItemId()) {
            case R.id.filtrarGenero:
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecyclerViewFragmentFiltro recyclerViewFragmentFiltro = new RecyclerViewFragmentFiltro();
                recyclerViewFragmentFiltro.show(fragmentManager, "FILTROS");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
    }

    @Override
    public void seleccionaronAlActor(int posicion, List<Cast> castList) {
        Intent intent = new Intent(ActivityDetalle.this, ActivityDetalleActores.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TMDBHelper.POSICION, posicion);
        bundle.putSerializable(TMDBHelper.SERIALIZABLE, (Serializable) castList);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class EscuchadorMenuNavigationView implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            menuItemSeleccionado(item);
            return true;
        }
    }

    private void menuItemSeleccionado(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.itemInicio:
                startActivity(new Intent(this, ActivityInicio.class));
                break;

            case R.id.itemUsuario:
                if (usuarioActual != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FragmentPerfil fragmentPerfil = new FragmentPerfil();
                    fragmentTransaction.replace(R.id.contenedor_InicioActivity, fragmentPerfil);
                    fragmentTransaction.commit();
                } else {
                    Intent intent = new Intent(ActivityDetalle.this, ActivityPerfil.class);
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
                Intent intent = new Intent(this, ActivityLogin.class);
                startActivity(intent);
        }

        drawerLayout.closeDrawers();
    }


}
