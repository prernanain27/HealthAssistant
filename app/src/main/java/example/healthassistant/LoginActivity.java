package example.healthassistant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.data;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE = 122;
    private SignInButton google_login;
    private EditText emailEditText;
    private EditText password;
    private Button newUser;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions signInOptions;
    private Button login;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();


        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        google_login = (SignInButton) findViewById(R.id.Google_sign_in);
        newUser = (Button) findViewById(R.id.new_user);
        password = (EditText) findViewById(R.id.password);
      emailEditText = (EditText) findViewById(R.id.email);
        login = (Button) findViewById(R.id.logIn);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , PHR.class);
                startActivity(intent);
            }
        });


        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, REQUEST_CODE);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String pass = password.getText().toString();

                email = email.trim();
                pass = pass.trim();

                if (email.isEmpty() || pass.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.login_error_message)
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    String[] projection = {DbContract.DbEntry.COLUMN_EMAIL, DbContract.DbEntry.COLUMN_PASSWORD};
                    Cursor data = mDb.query(DbContract.DbEntry.TABLE_NAME,projection, DbContract.DbEntry._ID + " = "+ emailEditText.getText().toString(),
                            null,null,null,null,null);
                    if(data.getCount()==0){
                        Toast.makeText(LoginActivity.this, "Either E-mail is wrong or not registered",Toast.LENGTH_SHORT).show();

                                    } else {

                                    }


                }
            }
        });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            GoogleSignInResult user = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount profile_data = user.getSignInAccount();

            //String email = profile_data.getEmail().toString();
           // User.setEmail(email);
            Intent i = new Intent(this, HomeScreen.class);
            startActivity(i);
           // addData(email,"");


        }

    }
    public void addData(String email, String password){
        DbHelper db = new DbHelper(this);
        mDb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.DbEntry.COLUMN_EMAIL,email);
        cv.put(DbContract.DbEntry.COLUMN_PASSWORD,password);
        long result = mDb.insert(DbContract.DbEntry.TABLE_NAME,null,cv);
        if(result!=-1)
            Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}
