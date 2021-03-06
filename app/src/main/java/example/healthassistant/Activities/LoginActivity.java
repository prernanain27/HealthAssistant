package example.healthassistant.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.R;

import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_INTERFERER_ID;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_MED_ID;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_MIN_FROM;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_MIN_TO;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_MAX_DOSE;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_MED_NAME;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_MIN_DOSE;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_SEPARATION;

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
    SQLiteOpenHelper db;
    String email;
    String pass;
    private TextView loginErrorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        google_login = (SignInButton) findViewById(R.id.Google_sign_in);
        newUser = (Button) findViewById(R.id.new_user);
        password = (EditText) findViewById(R.id.password);
        emailEditText = (EditText) findViewById(R.id.email);
        login = (Button) findViewById(R.id.logIn);
        loginErrorMessage = (TextView) findViewById(R.id.loginErrorMessage);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , NewUserData.class);
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
                 email = emailEditText.getText().toString();
                 pass = password.getText().toString();

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
                    String where = email;
                    db = new DbHelper(getApplicationContext());
                    Log.d("LoginActivity", " entered user login.setOnClickListner");
                    mDb = db.getWritableDatabase();
                    String[] projection = {DbContract.DbEntry.COLUMN_EMAIL, DbContract.DbEntry.COLUMN_PASSWORD};
                    Cursor data = mDb.query( DbContract.DbEntry.TABLE_NAME, projection,DbContract.DbEntry.COLUMN_EMAIL + " = ?",new String[]{where} , null, null, null, null);
                    int indexEmail = data.getColumnIndex(DbContract.DbEntry.COLUMN_EMAIL);
                    int indexPassword = data.getColumnIndex(DbContract.DbEntry.COLUMN_PASSWORD);
                    try {
                        if (data != null) {
                            data.moveToFirst();
                            Log.d("Login:GetDBEmail", data.getString(indexEmail));
                        }
                        if(!data.getString(indexEmail).equalsIgnoreCase(email) | !data.getString(indexPassword).equalsIgnoreCase(pass)){
                            loginErrorMessage.setText("Incorrect Email or Password! Please enter again.");
                            Toast.makeText(LoginActivity.this, "Incorrect Email or Password.",Toast.LENGTH_LONG).show();

                        } else {
                            Intent homeScreen = new Intent(getApplicationContext(), HomeScreen.class);
                            startActivity(homeScreen);

                        }
                        if (!email.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Invalid Email Address")
                                    .setTitle("Error")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }catch(Exception e){
                        loginErrorMessage.setText("Email not registered. Click on 'New User' button to register!");
                        Toast.makeText(LoginActivity.this, "Email not registered. Please click on 'New User' button to register!",Toast.LENGTH_LONG).show();
                        Log.d("Exception Occured: " , "Email Not Registered: " +e);

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
            if(profile_data != null) {
                String g_mail = profile_data.getEmail().toString();
                String where = g_mail;
                db = new DbHelper(getApplicationContext());
                Log.d("LoginActivity", " entered user login.setOnClickListner");
                mDb = db.getWritableDatabase();

                String[] projection = {DbContract.DbEntry.COLUMN_EMAIL, DbContract.DbEntry.COLUMN_PASSWORD};

                Cursor data1 = mDb.query(DbContract.DbEntry.TABLE_NAME, projection, DbContract.DbEntry.COLUMN_EMAIL + " = ?", new String[]{where}, null, null, null, null);
                if (data1.getCount() > 0) {
                    Intent i = new Intent(this, HomeScreen.class);
                    startActivity(i);
                } else {
                 //   NewUserData nw = new NewUserData();
                 //   nw.addData(g_mail, "");
                    addData(g_mail, " ");
                    Intent i = new Intent(this, AddPHRActivity.class);
                    startActivity(i);
                }
            }
            else
                Toast.makeText(this, "Something is wrong with sign-in",Toast.LENGTH_SHORT).show();

        }

    }

    public void addData(String email, String password) {
        db = new DbHelper(this);
        mDb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.DbEntry.COLUMN_EMAIL, email);
        cv.put(DbContract.DbEntry.COLUMN_PASSWORD, password);
        try {
            long result = mDb.insert(DbContract.DbEntry.TABLE_NAME, null, cv);

            if (result != -1)
                Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        catch (SQLiteException ex){
            String s = ex.getMessage();
        }

    }





}
