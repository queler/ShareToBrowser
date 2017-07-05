package aq.com.sharetobrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        links = new ArrayList<>();
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }

    /**
     * Test the extra test in the intent
     * If it contains link set the list view to show them
     * If there's only one, send a webIntent
     * If there's more let the user pick the one it wants to open
     * If it's empty will let the user try to send a webIntent anyway
     * @param intent to be handled
     */
    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            testExtra(sharedText);
        }
        TextView resultTV=(TextView) findViewById(R.id.resultTV);
        if (links.size() > 0) {
            resultTV.setText(R.string.links_found);
            setListView();
            if (links.size() == 1)
                sendWebIntent(links.get(0));
        }else{
            this.SetNoLinksFoundView(sharedText);
        }
    }

    /**
     * Parse the extra looking for links
     * Split it by withespace then test the results to see if it's a link
     * If it's a valid link, add it to links, the arrayList of valid links
     * @param strExtra extra content of the incoming intent
     */
    private void testExtra(String strExtra) {
        //pattern by John Gruber https://gist.github.com/gruber/8891611
        String pattern ="(?i)\\b((?:https?:(?:/{1,3}|[a-z0-9%])|[a-z0-9.\\-]+[.](?:com|net|org|edu|gov|mil|aero|asia|biz|cat|coop|info|int|jobs|mobi|museum|name|post|pro|tel|travel|xxx|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cs|cu|cv|cx|cy|cz|dd|de|dj|dk|dm|do|dz|ec|ee|eg|eh|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|Ja|sk|sl|sm|sn|so|sr|ss|st|su|sv|sx|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)/)(?:[^\\s()<>{}\\[\\]]+|\\([^\\s()]*?\\([^\\s()]+\\)[^\\s()]*?\\)|\\([^\\s]+?\\))+(?:\\([^\\s()]*?\\([^\\s()]+\\)[^\\s()]*?\\)|\\([^\\s]+?\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’])|(?:(?<!@)[a-z0-9]+(?:[.\\-][a-z0-9]+)*[.](?:com|net|org|edu|gov|mil|aero|asia|biz|cat|coop|info|int|jobs|mobi|museum|name|post|pro|tel|travel|xxx|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cs|cu|cv|cx|cy|cz|dd|de|dj|dk|dm|do|dz|ec|ee|eg|eh|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|Ja|sk|sl|sm|sn|so|sr|ss|st|su|sv|sx|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)\\b/?(?!@)))";
        String[] tabExtra = strExtra.split("\\s+");
        for (String str : tabExtra) {
            if (Pattern.matches(pattern, str))
                links.add(str);
        }
    }

    /**
     * Set the list view with the links found
     * Set each link in the view to sent a webintent on click
     */
    private void setListView() {
        ListView listViewLinks = (ListView) findViewById(R.id.linksLV);
        String[] listItems = new String[links.size()];

        for (int i = 0; i < links.size(); i++)
            listItems[i] = links.get(i);

        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, listItems);
        listViewLinks.setAdapter(adapter);

        listViewLinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String link = (String) parent.getItemAtPosition(position);
                sendWebIntent(link);
            }
        });
    }

    /**
     * Send a webIntent with the url given in the parameter
     * @param url to be opened
     */
    private void sendWebIntent(String url) {
        Uri uri = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
        if (webIntent.resolveActivity(getPackageManager()) != null)
            startActivity(Intent.createChooser(webIntent, ""));
    }

    /**
     * Change the textView value to tell that no result where found
     * Add a button that allow the user to send a webIntent with the
     * sharedText
     * @param sharedText from the initial Intent
     */
    private void SetNoLinksFoundView(final String sharedText){
        TextView resultTV=(TextView) findViewById(R.id.resultTV);
        resultTV.setText(R.string.not_found);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.globalLayout);
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParam.addRule(RelativeLayout.BELOW, R.id.resultTV);

        Button btSendIntent=new Button(this);
        btSendIntent.setText(R.string.open_anyway);
        btSendIntent.setLayoutParams(layoutParam);
        btSendIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWebIntent(sharedText);
            }
        });
        layout.addView(btSendIntent);
    }

}

