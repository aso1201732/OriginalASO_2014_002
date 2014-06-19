package jp.ac.st.asojuku.original2014002;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	/**
	 * @param context　呼び出しコンテクスト
	 * @param name 利用DB名
	 * @param factory カーソルファクトリー
	 * @param version DBバージョン
	 */
	public MySQLiteOpenHelper(Context context) {

		super(context, "20140021201732.sqlite3", null, 1);

	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自動生成されたメソッド・スタブ
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				"Hitokoto ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , phrase TEXT )");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drip table Hitokoto ");
		onCreate(db);
		// TODO 自動生成されたメソッド・スタブ

		}

}
