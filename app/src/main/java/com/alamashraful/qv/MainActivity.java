package com.alamashraful.qv;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.jqurantree.arabic.encoding.EncodingType;
import org.jqurantree.orthography.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = getApplicationContext();

        System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button search = (Button) findViewById(R.id.button);

        search.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText editText = (EditText) findViewById(R.id.editText);
                final TextView textView = (TextView) findViewById(R.id.textView2 );
                textView.setMovementMethod( new ScrollingMovementMethod() );

                textView.setText( "" );

                String chapterNumTxt = editText.getText().toString();
                Integer chapterNum = Integer.parseInt( chapterNumTxt );

                Chapter chapter = Document.getChapter( chapterNum );

                Integer verseCount = chapter.getVerseCount();
                StringBuilder sb = new StringBuilder();

                for( int i =1; i<=verseCount; i++ ) {

                    Location location = new Location(chapterNum, i);
                    Verse verse = Document.getVerse(location);

                    Iterable<Token> tokens = verse.getTokens();
                    Iterator<Token> tokenIterator = tokens.iterator();

                    List<String> tokenUnicode = new ArrayList<>();


                    while (tokenIterator.hasNext()) {

                        Token token = tokenIterator.next();
                        tokenUnicode.add(token.toString(EncodingType.Unicode));
                        sb.append("\n" + tokenUnicode.get(tokenUnicode.size() - 1));
                    }
                }
                textView.setText(sb.toString());
            }
        });
    }
}
