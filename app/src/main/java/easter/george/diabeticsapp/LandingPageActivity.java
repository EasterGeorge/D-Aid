package easter.george.diabeticsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LandingPageActivity extends AppCompatActivity {
    private static final String TAG = "LandingPageActivity";
    private static final String ARG_NAME = "username";
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    Button logOut,disConnect;

    public static void startActivity(Context context, String username) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ARG_NAME, username);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textViewWelcome);
        if (getIntent().hasExtra(ARG_NAME)) {
            textView.setText(String.format("Welcome - %s", getIntent().getStringExtra(ARG_NAME)));
        }
        logOut = (Button)findViewById(R.id.buttonLogout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             signOut();
            }
        });
        disConnect= (Button) findViewById(R.id.buttonDisconnect);
            disConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    revokeAccess();
                }
            });
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void signOut() {
        firebaseAuth.signOut();
        googleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.w(TAG, "Signed out of google");
                    }
                });
    }
    private void revokeAccess() {
        firebaseAuth.signOut();
        googleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.w(TAG, "Revoked Access");
                    }
                });
    }
}