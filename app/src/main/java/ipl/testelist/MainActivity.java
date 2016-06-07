            package ipl.testelist;

            import java.io.File;
            import java.security.Key;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.List;
            import java.util.Set;

            import android.app.Dialog;
            import android.app.DialogFragment;
            import android.view.ViewGroup.LayoutParams;
            import android.app.Activity;
            import android.app.AlertDialog;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.drawable.AnimationDrawable;
            import android.graphics.drawable.Drawable;
            import android.net.Uri;
            import android.os.Bundle;
            import android.provider.MediaStore;
            import android.renderscript.Sampler;
            import android.view.Gravity;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.ArrayAdapter;
            import android.widget.Button;
            import android.widget.EditText;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.ListView;
            import android.widget.PopupWindow;
            import android.widget.RatingBar;
            import android.widget.SimpleAdapter;
            import android.widget.Spinner;
            import android.widget.Toast;

            public class MainActivity extends Activity {
                private List<HashMap<String,String>> aList;
                private HashMap<String, String> hm;
                private String bmImg;
                private static final int RESULT_LOAD_IMAGE = 1;



                int[] list_image = new int[]{
                        /*R.drawable.acdc,
                        R.drawable.bonjovi,
                        R.drawable.ironmaiden,
                        R.drawable.direstraits,
                        R.drawable.rihanna,*/
                };

                public String[] album = new String[] {
                        /*"Back In Black",
                        "Bon Jovi Greatest",
                        "The Book Of Souls",
                        "Brothers In Arms",
                        "Take A Bow",*/
                };

                public String[] artista = new String[]{
                        /*"AC/DC",
                        "Bon Jovi",
                        "Iron Maiden",
                        "Dire Straits",
                        "Rihanna",*/
                };

                public String[] ano = new String[]{
                       /* "1980",
                        "2010",
                        "2015",
                        "1985",
                        "2008",*/
                };
                public String[] editora = new String[]{
                        /*"1980",
                        "2010",
                        "2015",
                        "1985",
                        "2008",*/
                };

                public String[] rating = new String[]{
                       /* "5",
                        "4",
                        "5",
                        "3",
                        "0",*/
                };
                private AlertDialog alertDialog;


                /** Called when the activity is first created. */
                @Override
                public void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);

                    SharedPreferences sharedPreferences = getSharedPreferences("Albuns", 0);
                    HashSet stringSet = (HashSet) sharedPreferences.getStringSet("albunsKey", new HashSet<String>());

                    // Each row in the list stores country name, currency and flag
                    //List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                    aList = new ArrayList<HashMap<String,String>>(stringSet);



                    for(int i=0;i<album.length;i++){
                        HashMap<String, String> hm = new HashMap<String,String>();
                        hm.put("list_image", Integer.toString(list_image[i]) );
                        hm.put("album", getString(R.string.album)+": " +  album[i]);
                        hm.put("artista",getString(R.string.artist)+": " + artista[i]);
                        hm.put("editora","Editora"+": "+ editora[i]);
                        hm.put("ano",getString(R.string.year)+": " + ano[i]);

                        if(rating[i].equals("0")){
                            hm.put("rating",Integer.toString(R.drawable.star0));
                        }
                        else if(rating[i].equals("1")){
                            hm.put("rating",Integer.toString(R.drawable.star1));
                        }
                        else if(rating[i].equals("2")){
                            hm.put("rating",Integer.toString(R.drawable.star2));
                        }
                        else if(rating[i].equals("3")){
                            hm.put("rating",Integer.toString(R.drawable.star3));
                        }
                        else if(rating[i].equals("4")){
                            hm.put("rating",Integer.toString(R.drawable.star4));
                        }
                        else if(rating[i].equals("5")){
                            hm.put("rating",Integer.toString(R.drawable.star5));
                        }

                        aList.add(hm);
                    }

                    // Keys used in Hashmap
                    String[] from = { "list_image","album","artista","ano","editora","rating" };

                    // Ids of views in listview_layout
                    int[] to = { R.id.list_image,R.id.album,R.id.artista,R.id.ano,R.id.editora, R.id.ratingc};

                    // Instantiating an adapter to store each items
                    // R.layout.listview_layout defines the layout of each item
                    SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

                    // Getting a reference to listview of main.xml layout file
                    final ListView listView = ( ListView ) findViewById(R.id.listView_contacts);

                    // Setting the adapter to the listView
                    listView.setAdapter(adapter);

                    Spinner spinner = (Spinner) findViewById(R.id.spinner_search);

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(adapter2);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                  HashMap<String,String> item = (HashMap<String,String>)listView.getItemAtPosition(position);

                            String artista = item.get("artista");
                            String[] a = artista.split(": ");
                            String art = a[1];
                            String album = item.get("album");
                            String[] al = album.split(": ");
                            String alb = al[1];

                            String query = art + " " + alb + "full album";
                            Intent intent = new Intent(Intent.ACTION_SEARCH);
                            //intent.setAction("com.google.android.youtube",cl)
                            intent.setPackage("com.google.android.youtube");
                            intent.putExtra("query", query);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                            /*aList.remove(position);
                            // Keys used in Hashmap
                            String[] from = { "list_image","album","artista","ano","editora","rating" };
                            // Ids of views in listview_layout
                            int[] to = { R.id.list_image,R.id.album,R.id.artista,R.id.ano,R.id.editora,R.id.ratingc};
                            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);
                            ListView listView = (ListView) findViewById(R.id.listView_contacts);
                            listView.setAdapter(adapter);*/

                            //Toast.makeText(MainActivity.this, "Apagaste " + item.get("album"), Toast.LENGTH_SHORT).show();







                        }
                    });


                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String,String> item = (HashMap<String,String>)listView.getItemAtPosition(position);
                            aList.remove(position);
                            //Keys used in Hashmap
                            String[] from = { "list_image","album","artista","ano","editora","rating" };
                            //Ids of views in listview_layout
                            int[] to = { R.id.list_image,R.id.album,R.id.artista,R.id.ano,R.id.editora,R.id.ratingc};
                            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);
                            ListView listView = (ListView) findViewById(R.id.listView_contacts);
                            listView.setAdapter(adapter);
                            Toast.makeText(MainActivity.this, "Apagaste " + item.get("album"), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                }


                @Override
                protected void onStop(){
                   super.onStop();
                    Toast.makeText(MainActivity.this, R.string.album_added, Toast.LENGTH_SHORT).show();

                    //guardar os albuns para a shared preferences
                    SharedPreferences sp = getSharedPreferences("Albuns",0);
                    SharedPreferences.Editor editor = sp.edit();
                    HashSet albunsSet = new HashSet(aList);
                    editor.putStringSet("albunsKey",albunsSet);
                    editor.commit();



                }


                public void onClick_search(View view) {

                    //ir buscar referência para edittext, spinner e listview
                    EditText et = (EditText) findViewById(R.id.editText_search);
                    Spinner sp = (Spinner) findViewById(R.id.spinner_search);
                    ListView lv = (ListView) findViewById(R.id.listView_contacts);

                    //pesquisar o termo nos contactos e guardar o resultado da pesquisa numa lista

                    String termo = et.getText().toString().toLowerCase();
                    String itemSeleccionado = sp.getSelectedItem().toString();

                    if (termo.equals("")) {


                         // Keys used in Hashmap
                        String[] from = {"list_image", "album", "artista", "ano", "editora","rating"};

                        // Ids of views in listview_layout
                        int[] to = {R.id.list_image, R.id.album, R.id.artista, R.id.ano,R.id.editora ,R.id.ratingc};

                        // Instantiating an adapter to store each items
                        // R.layout.listview_layout defines the layout of each item
                        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

                        // Getting a reference to listview of main.xml layout file
                        ListView listView = (ListView) findViewById(R.id.listView_contacts);

                        // Setting the adapter to the listView

                        lv.setAdapter(adapter);

                        Toast.makeText(MainActivity.this, R.string.showingall, Toast.LENGTH_SHORT).show();
                    } else {
                        List<HashMap<String, String>> resultados = new ArrayList<HashMap<String, String>>();

                        if (itemSeleccionado.equals(getString(R.string.all))) {

                            for (int i = 0; i < aList.size(); i++) {

                                String valor = aList.get(i).toString().toLowerCase();
                                boolean contem = valor.contains(termo);

                                //se for verdadeiro

                                if (contem) {

                                    HashMap<String, String> re = new HashMap<String, String>();
                                    re.put("list_image", aList.get(i).get("list_image"));
                                    re.put("album", "" + aList.get(i).get("album"));
                                    re.put("artista", "" + aList.get(i).get("artista"));
                                    re.put("ano", "" + aList.get(i).get("ano"));
                                    re.put("editora",""+ aList.get(i).get("editora"));
                                    if (aList.get(i).get("rating").equals("2130837590")) {
                                        re.put("rating", Integer.toString(R.drawable.star0));
                                    } else if (aList.get(i).get("rating").equals("2130837591")) {
                                        re.put("rating", Integer.toString(R.drawable.star1));
                                    } else if (aList.get(i).get("rating").equals("2130837592")) {
                                        re.put("rating", Integer.toString(R.drawable.star2));
                                    } else if (aList.get(i).get("rating").equals("2130837593")) {
                                        re.put("rating", Integer.toString(R.drawable.star3));
                                    } else if (aList.get(i).get("rating").equals("2130837594")) {
                                        re.put("rating", Integer.toString(R.drawable.star4));
                                    } else if (aList.get(i).get("rating").equals("2130837595")) {
                                        re.put("rating", Integer.toString(R.drawable.star5));
                                    }

                                    resultados.add(re);
                                }

                            }

                        }else if (itemSeleccionado.equals(getString(R.string.artist))) {
                            for (int i = 0; i < aList.size(); i++) {

                                String valor = aList.get(i).get("artista").toLowerCase();
                                boolean contem = valor.contains(termo);

                                //se for verdadeiro

                                if (contem) {

                                    HashMap<String, String> re = new HashMap<String, String>();
                                    re.put("list_image", aList.get(i).get("list_image"));
                                    re.put("album", "" + aList.get(i).get("album"));
                                    re.put("artista", "" + aList.get(i).get("artista"));
                                    re.put("ano", "" + aList.get(i).get("ano"));
                                    re.put("editora",""+ aList.get(i).get("editora"));

                                    if (aList.get(i).get("rating").equals("2130837590")) {
                                        re.put("rating", Integer.toString(R.drawable.star0));
                                    } else if (aList.get(i).get("rating").equals("2130837591")) {
                                        re.put("rating", Integer.toString(R.drawable.star1));
                                    } else if (aList.get(i).get("rating").equals("2130837592")) {
                                        re.put("rating", Integer.toString(R.drawable.star2));
                                    } else if (aList.get(i).get("rating").equals("2130837593")) {
                                        re.put("rating", Integer.toString(R.drawable.star3));
                                    } else if (aList.get(i).get("rating").equals("2130837594")) {
                                        re.put("rating", Integer.toString(R.drawable.star4));
                                    } else if (aList.get(i).get("rating").equals("2130837595")) {
                                        re.put("rating", Integer.toString(R.drawable.star5));
                                    }

                                    resultados.add(re);
                                }
                             }
                        }else if (itemSeleccionado.equals(getString(R.string.album))) {
                            for (int i = 0; i < aList.size(); i++) {
                                String valor = aList.get(i).get("album").toLowerCase();
                                boolean contem = valor.contains(termo);

                                //se for verdadeiro

                                if (contem) {

                                    HashMap<String, String> re = new HashMap<String, String>();
                                    re.put("list_image", aList.get(i).get("list_image"));
                                    re.put("album", "" + aList.get(i).get("album"));
                                    re.put("artista", "" + aList.get(i).get("artista"));
                                    re.put("ano", "" + aList.get(i).get("ano"));
                                    re.put("editora",""+ aList.get(i).get("editora"));

                                    if (aList.get(i).get("rating").equals("2130837590")) {
                                        re.put("rating", Integer.toString(R.drawable.star0));
                                    } else if (aList.get(i).get("rating").equals("2130837591")) {
                                        re.put("rating", Integer.toString(R.drawable.star1));
                                    } else if (aList.get(i).get("rating").equals("2130837592")) {
                                        re.put("rating", Integer.toString(R.drawable.star2));
                                    } else if (aList.get(i).get("rating").equals("2130837593")) {
                                        re.put("rating", Integer.toString(R.drawable.star3));
                                    } else if (aList.get(i).get("rating").equals("2130837594")) {
                                        re.put("rating", Integer.toString(R.drawable.star4));
                                    } else if (aList.get(i).get("rating").equals("2130837595")) {
                                        re.put("rating", Integer.toString(R.drawable.star5));
                                    }

                                    resultados.add(re);
                                }

                            }
                        }else if (itemSeleccionado.equals(getString(R.string.year))) {
                            for (int i = 0; i < aList.size(); i++) {
                                String valor = aList.get(i).get("ano").toLowerCase();
                                boolean contem = valor.contains(termo);

                                //se for verdadeiro

                                if (contem) {

                                    HashMap<String, String> re = new HashMap<String, String>();
                                    re.put("list_image", aList.get(i).get("list_image"));
                                    re.put("album", "" + aList.get(i).get("album"));
                                    re.put("artista", "" + aList.get(i).get("artista"));
                                    re.put("ano", "" + aList.get(i).get("ano"));
                                    re.put("editora", "" + aList.get(i).get("editora"));

                                    if (aList.get(i).get("rating").equals("2130837590")) {
                                        re.put("rating", Integer.toString(R.drawable.star0));
                                    } else if (aList.get(i).get("rating").equals("2130837591")) {
                                        re.put("rating", Integer.toString(R.drawable.star1));
                                    } else if (aList.get(i).get("rating").equals("2130837592")) {
                                        re.put("rating", Integer.toString(R.drawable.star2));
                                    } else if (aList.get(i).get("rating").equals("2130837593")) {
                                        re.put("rating", Integer.toString(R.drawable.star3));
                                    } else if (aList.get(i).get("rating").equals("2130837594")) {
                                        re.put("rating", Integer.toString(R.drawable.star4));
                                    } else if (aList.get(i).get("rating").equals("2130837595")) {
                                        re.put("rating", Integer.toString(R.drawable.star5));
                                    }

                                    resultados.add(re);
                                }

                            }
                        }else if (itemSeleccionado.equals(getString(R.string.editora))) {
                                for (int i = 0; i < aList.size(); i++) {
                                    String valor = aList.get(i).get("editora").toLowerCase();
                                    boolean contem = valor.contains(termo);

                                    //se for verdadeiro

                                    if (contem) {

                                        HashMap<String, String> re = new HashMap<String, String>();
                                        re.put("list_image", aList.get(i).get("list_image"));
                                        re.put("album", "" + aList.get(i).get("album"));
                                        re.put("artista", "" + aList.get(i).get("artista"));
                                        re.put("ano", "" + aList.get(i).get("ano"));
                                        re.put("editora",""+ aList.get(i).get("editora"));

                                        if (aList.get(i).get("rating").equals("2130837590")) {
                                            re.put("rating", Integer.toString(R.drawable.star0));
                                        } else if (aList.get(i).get("rating").equals("2130837591")) {
                                            re.put("rating", Integer.toString(R.drawable.star1));
                                        } else if (aList.get(i).get("rating").equals("2130837592")) {
                                            re.put("rating", Integer.toString(R.drawable.star2));
                                        } else if (aList.get(i).get("rating").equals("2130837593")) {
                                            re.put("rating", Integer.toString(R.drawable.star3));
                                        } else if (aList.get(i).get("rating").equals("2130837594")) {
                                            re.put("rating", Integer.toString(R.drawable.star4));
                                        } else if (aList.get(i).get("rating").equals("2130837595")) {
                                            re.put("rating", Integer.toString(R.drawable.star5));
                                        }

                                        resultados.add(re);
                                    }

                                }


                        }else if (itemSeleccionado.equals(getString(R.string.rating))) {

                            for (int i = 0; i < aList.size(); i++) {

                                String valor = String.valueOf(Integer.parseInt(aList.get(i).get("rating")));
                                String valor2 = new String();
                                if (valor.equals("2130837590")) {
                                    valor2 = "0";
                                }else if (valor.equals("2130837591")) {
                                    valor2 = "1";
                                }else if (valor.equals("2130837592")) {
                                    valor2 = "2";
                                }else if (valor.equals("2130837593")) {
                                    valor2 = "3";
                                }else if (valor.equals("2130837594")) {
                                    valor2 = "4";
                                }else if (valor.equals("2130837595")) {
                                    valor2 = "5";
                                }

                                boolean contem = valor2.equals((termo));

                                //se for verdadeiro

                                if (contem) {

                                    HashMap<String, String> re = new HashMap<String, String>();
                                    re.put("list_image", aList.get(i).get("list_image"));
                                    re.put("album", "" + aList.get(i).get("album"));
                                    re.put("artista", "" + aList.get(i).get("artista"));
                                    re.put("ano", "" + aList.get(i).get("ano"));
                                    re.put("editora",""+ aList.get(i).get("editora"));

                                    if (aList.get(i).get("rating").equals("2130837590")) {
                                        re.put("rating", Integer.toString(R.drawable.star0));
                                    } else if (aList.get(i).get("rating").equals("2130837591")) {
                                        re.put("rating", Integer.toString(R.drawable.star1));
                                    } else if (aList.get(i).get("rating").equals("2130837592")) {
                                        re.put("rating", Integer.toString(R.drawable.star2));
                                    } else if (aList.get(i).get("rating").equals("2130837593")) {
                                        re.put("rating", Integer.toString(R.drawable.star3));
                                    } else if (aList.get(i).get("rating").equals("2130837594")) {
                                        re.put("rating", Integer.toString(R.drawable.star4));
                                    } else if (aList.get(i).get("rating").equals("2130837595")) {
                                        re.put("rating", Integer.toString(R.drawable.star5));
                                    }

                                    resultados.add(re);
                                }

                            }
                        }
                        if (resultados.isEmpty()){


                            // Keys used in Hashmap
                            String[] from = {"list_image", "album", "artista", "ano","editora", "rating"};

                            // Ids of views in listview_layout
                            int[] to = {R.id.list_image, R.id.album, R.id.artista, R.id.ano,R.id.editora ,R.id.ratingc};

                            // Instantiating an adapter to store each items
                            // R.layout.listview_layout defines the layout of each item
                            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

                            // Getting a reference to listview of main.xml layout file
                            ListView listView = (ListView) findViewById(R.id.listView_contacts);

                            // Setting the adapter to the listView

                            lv.setAdapter(adapter);

                            Toast.makeText(MainActivity.this, R.string.no_result, Toast.LENGTH_SHORT).show();
                        }else {


                            // Keys used in Hashmap
                            String[] from = {"list_image", "album", "artista", "ano", "editora", "rating"};

                            // Ids of views in listview_layout
                            int[] to = {R.id.list_image, R.id.album, R.id.artista, R.id.ano, R.id.editora, R.id.ratingc};

                            // Instantiating an adapter to store each items
                            // R.layout.listview_layout defines the layout of each item
                            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), resultados, R.layout.listview_layout, from, to);

                            // Getting a reference to listview of main.xml layout file
                            ListView listView = (ListView) findViewById(R.id.listView_contacts);

                            // Setting the adapter to the listView

                            lv.setAdapter(adapter);

                            Toast.makeText(MainActivity.this, R.string.show_result, Toast.LENGTH_SHORT).show();
                        }



                    }

                }
                 public void onClick_add(final View view) {

                    // otherwise, show a dialog to ask for their name
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    final LayoutInflater inflater = this.getLayoutInflater();

                    builder.setView(inflater.inflate(R.layout.dialog, null));
                    builder.setTitle(R.string.enter_album);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            AlertDialog al = (AlertDialog) dialog;

                            //ImageView im = (ImageView) al.findViewById(R.id.imageAlbum);
                            EditText input_nalbum = (EditText) al.findViewById(R.id.nalbum);
                            EditText input_nartista = (EditText) al.findViewById(R.id.nartist);
                            EditText input_anoAlbum = (EditText) al.findViewById(R.id.anoAlbum);
                            EditText input_neditora = (EditText) al.findViewById(R.id.neditora);
                            RatingBar input_rating = (RatingBar) al.findViewById(R.id.ratingBar);

                            //im.setImageResource(R.drawable.nophoto);

                            String nalbum = input_nalbum.getText().toString();
                            String nartista = input_nartista.getText().toString();
                            String anoAlbum = input_anoAlbum.getText().toString();
                            String neditora = input_neditora.getText().toString();
                            String rating = String.valueOf(input_rating.getRating());
/*gaita*/
                            if (nalbum.isEmpty()&& nartista.isEmpty()) {

                                Toast.makeText(MainActivity.this, R.string.no_album_add, Toast.LENGTH_SHORT).show();

                            } else {

                                HashMap<String, String> hm = new HashMap<String,String>();
                                if(bmImg==null){
                                    hm.put("list_image",Integer.toString(R.drawable.nophoto));
                                }else{
                                    hm.put("list_image", bmImg);
                                }

                                hm.put("album", "Album : " + nalbum);
                                hm.put("artista","Artista : " + nartista);
                                hm.put("ano","Ano : " + anoAlbum);
                                hm.put("editora","Editora : " + neditora);

                                if(rating.equals("0.0")){
                                    hm.put("rating",Integer.toString(R.drawable.star0));
                                }
                                else if(rating.equals("1.0")){
                                    hm.put("rating",Integer.toString(R.drawable.star1));
                                }
                                else if(rating.equals("2.0")){
                                    hm.put("rating",Integer.toString(R.drawable.star2));
                                }
                                else if(rating.equals("3.0")){
                                    hm.put("rating",Integer.toString(R.drawable.star3));
                                }
                                else if(rating.equals("4.0")){
                                    hm.put("rating",Integer.toString(R.drawable.star4));
                                }
                                else if(rating.equals("5.0")){
                                    hm.put("rating",Integer.toString(R.drawable.star5));
                                }

                                aList.add(hm);

                             // Keys used in Hashmap
                            String[] from = { "list_image","album","artista","ano","editora","rating" };

                            // Ids of views in listview_layout
                            int[] to = { R.id.list_image,R.id.album,R.id.artista,R.id.ano,R.id.editora,R.id.ratingc};

                            // Instantiating an adapter to store each items
                            // R.layout.listview_layout defines the layout of each item
                            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

                            // Getting a reference to listview of main.xml layout file
                            ListView listView = ( ListView ) findViewById(R.id.listView_contacts);

                            // Setting the adapter to the listView
                            listView.setAdapter(adapter);

                                Toast.makeText(MainActivity.this, R.string.album_added, Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(MainActivity.this, R.string.canceled, Toast.LENGTH_SHORT).show();
                        }
                    });


                    builder.show();
                }

                public void onClick_LoadPicture(View view) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }

                @Override
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);

                    if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                        final View view = factory.inflate(R.layout.dialog, null);

                        // String picturePath contains the path of selected Image
                        bmImg = picturePath;

                        //ImageView imageView = ((ImageView) view.findViewById(R.id.imageAlbum));
                        //bmImg = BitmapFactory.decodeFile(picturePath);

                        //imageView.setImageResource(R.drawable.nophoto);

                    }
                }

                public void onClick_reset(View view) {
                    setContentView(R.layout.activity_main);

                    // Each row in the list stores country name, currency and flag
                    //List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
                    aList = new ArrayList<HashMap<String,String>>();


                    for(int i=0;i<album.length;i++){
                        HashMap<String, String> hm = new HashMap<String,String>();
                        hm.put("list_image", Integer.toString(list_image[i]) );
                        hm.put("album", getString(R.string.album)+": " +  album[i]);
                        hm.put("artista",getString(R.string.artist)+": " + artista[i]);
                        hm.put("ano",getString(R.string.year)+": " + ano[i]);
                        hm.put("editora",getString(R.string.neditora)+": "+editora[i]);

                        if(rating[i].equals("0")){
                            hm.put("rating",Integer.toString(R.drawable.star0));
                        }
                        else if(rating[i].equals("1")){
                            hm.put("rating",Integer.toString(R.drawable.star1));
                        }
                        else if(rating[i].equals("2")){
                            hm.put("rating",Integer.toString(R.drawable.star2));
                        }
                        else if(rating[i].equals("3")){
                            hm.put("rating",Integer.toString(R.drawable.star3));
                        }
                        else if(rating[i].equals("4")){
                            hm.put("rating",Integer.toString(R.drawable.star4));
                        }
                        else if(rating[i].equals("5")){
                            hm.put("rating",Integer.toString(R.drawable.star5));
                        }

                        aList.add(hm);
                    }

                    // Keys used in Hashmap
                    String[] from = { "list_image","album","artista","ano","editora","rating" };

                    // Ids of views in listview_layout
                    int[] to = { R.id.list_image,R.id.album,R.id.artista,R.id.ano,R.id.editora,R.id.ratingc};

                    // Instantiating an adapter to store each items
                    // R.layout.listview_layout defines the layout of each item
                    SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

                    // Getting a reference to listview of main.xml layout file
                    ListView listView = ( ListView ) findViewById(R.id.listView_contacts);

                    // Setting the adapter to the listView
                    listView.setAdapter(adapter);

                    Spinner spinner = (Spinner) findViewById(R.id.spinner_search);

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(adapter2);

                }
            }
