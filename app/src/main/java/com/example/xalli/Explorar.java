package com.example.xalli;
import com.example.xalli.models.PuntoInteres;
import com.example.xalli.models.Platillo;
import com.example.xalli.models.Leyenda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class Explorar extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ContentAdapter adapter; // usa el mismo adapter de antes

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.explorar, container, false);

        recyclerView = root.findViewById(R.id.recycler);
        progressBar = root.findViewById(R.id.progress);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        // 2️⃣ Agregar datos de prueba para que se vean las tarjetas
        List<ContentItem> prueba = new ArrayList<>();
        prueba.add(new ContentItem("Quesabirrias", "Platillo", "Las quesabirrias son tacos de tortilla de maíz rellenos de queso derretido y carne de birria deshebrada. Se sirven con consomé, cebolla, cilantro y salsa.", "Tequila"));
        prueba.add(new ContentItem("Cuachala", "Platillo", "Pollo o cerdo desmenuzado en un caldo espesado con masa de maíz, sazonado con tomates, chiles guajillo o pasilla, mejorana y orégano.", "Tequila"));
        prueba.add(new ContentItem("Carnes en su jugo", "Platillo", "Finas tiras de bistec de res cocinadas en caldo de tomate, chile y cebolla, con frijoles y tocino dorado. Servido con cilantro y cebolla.", "Tequila"));
        prueba.add(new ContentItem("Borrego al pastor (Borrego tatemado)", "Platillo", "Carne de borrego marinada con especias, envuelta en pencas de maguey y cocida lentamente bajo tierra en horno tradicional.", "Tapalpa"));
        prueba.add(new ContentItem("Pechero", "Platillo", "Guiso con carne de cerdo, garbanzos, elote, papas, calabazas y chile guajillo, cocido en olla de barro.", "Tapalpa"));
        prueba.add(new ContentItem("Ponche de granada o de zarzamora", "Platillo", "Bebida tradicional de frutas locales (granada, zarzamora, guayaba) con especias como canela, clavo y piloncillo.", "Tapalpa"));
        prueba.add(new ContentItem("Tamales de acelgas o de frijol", "Platillo", "Tamales de masa de maíz rellenos de acelgas tiernas o frijoles refritos, cocidos al vapor en hojas de maíz.", "Tapalpa"));
        prueba.add(new ContentItem("Pescado del Lago (frito o al mojo de ajo)", "Platillo", "Tilapia o charal preparados fritos o al mojo de ajo, servidos con arroz, ensalada, limón y tortillas.", "Ajijic"));
        prueba.add(new ContentItem("Pozole", "Platillo", "Caldo espeso a base de maíz cacahuazintle con carne de cerdo o pollo, acompañado de lechuga, rábano, cebolla y tostadas.", "Ajijic"));
        prueba.add(new ContentItem("Tacos de barbacoa", "Platillo", "Barbacoa de res o borrego, cocida en olla de presión con chiles secos, ajo y especias, servida con consomé.", "Ajijic"));
        prueba.add(new ContentItem("Pan Tachihual", "Platillo", "Pan tradicional horneado en leña, con sabor dulce, elaborado por la familia Martínez Velázquez desde generaciones.", "Ajijic"));

        prueba.add(new ContentItem("La Ruta del Tequila", "Puntos de interes", "Recorrido por las haciendas tequileras y destiler�as donde se produce la bebida más emblemática de M�xico  con degustaciones y tours guiados.", "Tequila"));
        prueba.add(new ContentItem("Volcán de Tequila", "Puntos de interes", "Formación volcánica imponente rodeada de paisajes agaveros  ideal para senderismo y fotografías panorámicas.", "Tequila"));
        prueba.add(new ContentItem("Plaza Principal de Tequila", "Puntos de interes", "Corazón del pueblo con su kiosco  templo de Santiago Apóstol y ambiente típico jalisciense.", "Tequila"));
        prueba.add(new ContentItem("Museo Nacional del Tequila", "Puntos de interes", "Espacio dedicado a la historia  cultura y tradición del tequila en México.", "Tequila"));
        prueba.add(new ContentItem("Miradores de Tapalpa", "Puntos de interes", "Sitios panorámicos que permiten observar la sierra y los paisajes boscosos de la región.", "Tapalpa"));
        prueba.add(new ContentItem("Piedrotas de Tapalpa", "Puntos de interes", "Formaciones rocosas gigantes donde se puede hacer senderismo  rappel y paseos en cuatrimoto.", "Tapalpa"));
        prueba.add(new ContentItem("Centro Histórico de Tapalpa", "Puntos de interes", "Calles empedradas  casas de teja y plazas que conservan el encanto colonial.", "Tapalpa"));
        prueba.add(new ContentItem("Cascada El Salto del Nogal", "Puntos de interes", "Una de las cascadas más altas de Jalisco  rodeada de naturaleza y con acceso para ecoturismo.", "Tapalpa"));
        prueba.add(new ContentItem("Malecón de Ajijic", "Puntos de interes", "Paseo junto al Lago de Chapala con vistas espectaculares  restaurantes y murales artísticos", "Ajijic"));
        prueba.add(new ContentItem("Parroquia de San Andrés", "Puntos de interes", "Iglesia principal de Ajijic con arquitectura colonial y gran valor histórico.", "Ajijic"));
        prueba.add(new ContentItem("Calles pintorescas de Ajijic", "Puntos de interes", "Llenas de murales galerías de arte y coloridas casas que muestran el espíritu artístico del pueblo.", "Ajijic"));
        prueba.add(new ContentItem("Lago de Chapala", "Puntos de interes", "El lago más grande de México  ideal para paseos en lancha  pesca y actividades recreativas.", "Ajijic"));

        prueba.add(new ContentItem("Semana Cultural", "Cultura", "Programa de música, danza y eventos culturales; incluye el Festival Internacional de Cine de Tequila (FICTEQ) y la conmemoración del establecimiento de la Villa de Tequila.", "Tequila"));
        prueba.add(new ContentItem("Dia de la Santa Cruz", "Cultura", "Peregrinaciones al Santuario con danzas y fuegos artificiales; tradición transmitida por generaciones en barrios y colonias.", "Tequila"));
        prueba.add(new ContentItem("Los Cantaritos", "Cultura", "Juego en el que se arrojan figuras de barro en forma de cántaros para celebrar la llegada del verano.", "Tequila"));
        prueba.add(new ContentItem("Día Nacional del Tequila", "Cultura", "Celebración en honor a la bebida emblemática de México.", "Tequila"));
        prueba.add(new ContentItem("Feria Nacional del Tequila", "Cultura", "Fiesta popular con exposición de productores, charreadas, coronación de la Reina, desfile, música y eventos patronales dedicados a la Virgen de la Inmaculada Concepción y Guadalupe.", "Tequila"));
        prueba.add(new ContentItem("Fiestas Patrias", "Cultura", "Tapalpa celebra con charreadas, corridas, música, Grito de Independencia y el desfile de carretas decoradas con combate de flores.", "Tapalpa"));
        prueba.add(new ContentItem("Fiestas en honor a la Virgen de Guadalupe", "Cultura", "Peregrinaciones, misas, rezos, serenatas y fuegos artificiales en devoción guadalupana.", "Tapalpa"));
        prueba.add(new ContentItem("Virgen de las Mercedes", "Cultura", "Fiesta religiosa con mañanitas, misas, procesiones y fuegos artificiales.", "Tapalpa"));
        prueba.add(new ContentItem("Desfile de Año Nuevo", "Cultura", "Vecinos organizan un desfile espontáneo con disfraces, carros decorados y comparsas; tradición satírica y comunitaria.", "Ajijic"));
        prueba.add(new ContentItem("Día de Reyes", "Cultura", "Evento comunitario en la plaza con una Rosca gigante, chocolate, piñatas y regalos para niños.", "Ajijic"));
        prueba.add(new ContentItem("Fiesta de San Sebastían", "Cultura", "Celebración en honor al santo patrono del barrio; incluye procesión, música, pan Tachihual y convivencias.", "Ajijic"));
        prueba.add(new ContentItem("Carnaval de Ajijic y los Sayacos", "Cultura", "Carnaval con personajes enmascarados que lanzan harina, desfiles, música y crítica social.", "Ajijic"));
        prueba.add(new ContentItem("Feria de la Capirotada", "Cultura", "Evento gastronómico que celebra el tradicional postre de Cuaresma; concurso de recetas, talleres y música en vivo.", "Ajijic"));
        prueba.add(new ContentItem("Verbena Jamaica del Pasado", "Cultura", "Feria popular que revive tradiciones antiguas con comida típica, vestimenta de época y juegos tradicionales.", "Ajijic"));
        prueba.add(new ContentItem("Via Crucis Viviente", "Cultura", "Representación comunitaria de la Pasión de Cristo en las calles y cerros de Ajijic.", "Ajijic"));
        prueba.add(new ContentItem("Día de la Santa Cruz", "Cultura", "Cruces decoradas con flores, pan Tachihual y tequila; altares comunitarios, música y convivencias.", "Ajijic"));

        adapter.setItems(prueba); // esto llena el adapter
        // Aquí llamas a tu lógica de carga desde Supabase
        //loadContent();

        return root;
    }

   }