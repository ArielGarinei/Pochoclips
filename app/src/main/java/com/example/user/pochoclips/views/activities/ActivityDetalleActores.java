package com.example.user.pochoclips.views.activities;

import android.content.Intent;
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
import com.example.user.pochoclips.controllers.ControladorCast;
import com.example.user.pochoclips.models.POJO.Cast;
import com.example.user.pochoclips.views.adapter.ViewPagerAdapterDetalleActores;
import com.example.user.pochoclips.views.fragments.FragmentPerfil;
import com.example.user.pochoclips.views.fragments.tab_layout.recycler_view.RecyclerViewFragmentFiltro;
import com.example.user.pochoclips.views.fragments.view_pager.ViewPagerFragmentDetalleActores;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ActivityDetalleActores extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapterDetalleActores viewPagerAdapterDetalleActores;
    private FirebaseUser usuarioActual;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView imageViewFotoUsuarioNavigationView;
    private TextView textViewNombreUsuarioNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_actores);

        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

        //---NavigationView---
        drawerLayout = findViewById(R.id.contenedor_DetalleActoresActivity);
        navigationView = findViewById(R.id.navigationView_DetalleActoresActivity);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new EscuchadorMenu());
        MenuItem itemCerrarSesion = navigationView.getMenu().findItem(R.id.itemCerrarSesion);
        if(usuarioActual == null) itemCerrarSesion.setVisible(false);
        else itemCerrarSesion.setVisible(true);

        View viewHeader = navigationView.getHeaderView(0);

        textViewNombreUsuarioNavigationView = viewHeader.findViewById(R.id.textViewNombreUsuario_NavigationViewHeader);
        imageViewFotoUsuarioNavigationView = viewHeader.findViewById(R.id.imageViewFotoUsuario_NavigationViewHeader);

        if (usuarioActual != null) {
            textViewNombreUsuarioNavigationView.setText(usuarioActual.getDisplayName());
            Glide.with(ActivityDetalleActores.this).load(usuarioActual.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imageViewFotoUsuarioNavigationView);
        }

        //-------AppBar-------
        Toolbar myToolbar = (Toolbar) findViewById(R.id.inicioToolBar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Pido el bundle que recibo desde ActivityDetalle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        List<Cast> castList = (List<Cast>) bundle.getSerializable(TMDBHelper.SERIALIZABLE);
        Integer posicion = bundle.getInt(TMDBHelper.POSICION);

        viewPager = findViewById(R.id.viewPagerDetalleActores);
        viewPagerAdapterDetalleActores = new ViewPagerAdapterDetalleActores(getSupportFragmentManager(),castList);
        viewPager.setAdapter(viewPagerAdapterDetalleActores);
        viewPager.setCurrentItem(posicion);
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
        if(toggle.onOptionsItemSelected(item)) return true;

        switch (item.getItemId()) {
            case R.id.filtrarGenero:
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecyclerViewFragmentFiltro recyclerViewFragmentFiltro = new RecyclerViewFragmentFiltro();
                recyclerViewFragmentFiltro.show(fragmentManager, "FILTROS");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class EscuchadorMenu implements NavigationView.OnNavigationItemSelectedListener {
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
                    Intent intent = new Intent(ActivityDetalleActores.this, ActivityLogin.class);
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
