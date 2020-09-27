package com.ro.maparea.db;

import android.annotation.SuppressLint;
import android.util.Log;

import android.database.sqlite.SQLiteDatabase;


public class DatabaseManagement {


    public  static SQLiteDatabase initDatabase() {

        SQLiteDatabase db =null;
        final String pkgNm="com.ro.maparea";
        final String dbNm="databaseselectionpf.db";
        try
        {
            db= SQLiteDatabase.openOrCreateDatabase("/data/data/" + pkgNm
                    + "/" + dbNm,null);
            //db= SQLiteDatabase.openOrCreateDatabase("/mnt/sdcard/"+ dbNm,null);


        }
        catch (Exception e)
        {
            Log.i("databse Error","" + e.getMessage());
            db = null;
        }
        return(db);
    }


    public static String createTable(String tblNm,String coulmnsAndTypes[]) {

        SQLiteDatabase db =initDatabase();
        String qry="";
        Log.i("Table-","Entering in 'createTable' function");
        String str=new String();
        try
        {
            if(coulmnsAndTypes.length%2==0)
            {
                for(int i=0;;)
                {
                    str=str+coulmnsAndTypes[i++]+" "+coulmnsAndTypes[i++];
                    if(i>=coulmnsAndTypes.length)
                        break;
                    str=str+" , ";
                }
                qry="CREATE TABLE IF NOT EXISTS " + tblNm + "("
                        + str + ");";
                db.execSQL(qry);
            }
            else
            {
                Log.i("Error", "Invalid columns");
            }
        }
        catch(Exception e)
        {
            Log.i("Error",""+e);
        }
        db.close();
        Log.i("Error","Exiting from 'createTable' function");
        Log.i("Error",qry);
        return(qry);
    }


    public static Boolean createIndex() {

        try
        {
            SQLiteDatabase db=initDatabase();
            db.execSQL("CREATE INDEX IF NOT EXISTS INDONVoterList ON VoterList(FullName ASC)");
            db.execSQL("CREATE INDEX IF NOT EXISTS INDONVoterList1 ON VoterList(CardNo ASC)");
            db.execSQL("CREATE INDEX IF NOT EXISTS INDONVoterList2 ON VoterList(SrNoInPart ASC)");
            db.execSQL("CREATE INDEX IF NOT EXISTS INDONVoterList3 ON VoterList(PartNo ASC)");
            return(true);
        }
        catch(Exception err)
        {
            //System.out.println(err.getMessage());
            Log.i("Sql Error", err.getMessage());
        }
        return(false);
    }

    @SuppressLint("LongLogTag")
    public static String insertIntoTable(String tblNm, int cnt, String colNms[], String val[]) {

        SQLiteDatabase db =initDatabase();
        String qry="";
        //if(!db.isOpen())
        String cols="",qMarks="";
        int i=0;
        try
        {
            for(i=0;;)
            {
                qMarks=qMarks+" ? ";
                if(++i==cnt)
                    break;
                qMarks=qMarks+" , ";
            }
            if(colNms!=null)
            {
                if(cnt==colNms.length)
                {
                    for(i=0;i<colNms.length;)
                    {
                        cols=cols+colNms[i++];
                        if(i>=colNms.length)
                            break;
                        cols=cols+" , ";
                    }
                    if(i>0)
                    {
                        qry="INSERT INTO "+tblNm+ " ( "+cols+" )VALUES ( "+ qMarks +" );";
                        db.execSQL(qry, val);
                        Log.i("Property Appli Error",qry);
                        Log.i("Property Appli Error","Record inserted successfully");
                    }
                }
                else
                {
                    Log.i("Property Appli Error", "Invalid number of columns");
                }
            }
            else
            {
                qry="INSERT INTO "+tblNm+ " VALUES ( "+ qMarks+ " );";
                db.execSQL(qry,val);
                Log.i("Property Appli Error","Record inserted successfully");
            }
        }
        catch(Exception e)
        {
            Log.i("Property Appli Error test", ""+e);
        }
        db.close();
        Log.i("Property Appli Error","Exiting from 'insertIntoTable' function");
        return(qry);
    }


    public static String updateTable(String tblNm,String colNms[],String whrClause,String val[]) {

        SQLiteDatabase db =initDatabase();
        String qry="";
        Log.i("In Update Qry :","Entering in 'updateTable' function");
        //if(!db.isOpen())
        String str="";
        int i;
        try
        {
            for(i=0;;)
            {
                str=str+colNms[i++] +" = ?";
                if(i>=colNms.length)
                {
                    break;
                }
                str=str+" , ";
            }
            if(whrClause!=null)
                str=str+whrClause;
            qry="update "+tblNm+" set "+str+" ; ";

            Log.i("Update Query", qry);
            db.execSQL(qry,val);
        }
        catch(Exception e)
        {
            Log.i("Property Appli Error", ""+e);
        }
        db.close();

        return(qry);
    }

}

