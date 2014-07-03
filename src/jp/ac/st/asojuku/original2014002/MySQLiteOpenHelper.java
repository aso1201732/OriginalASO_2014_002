package jp.ac.st.asojuku.original2014002;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

	/**
	 *  引数のフレーズをHitkotoテーブルにインサートするプライベートメソッド
	 *  @param SGLiteDatabase インサート先のDBのインスタンス変数
	 *  @param inputMsg インサートするメッセージ
	 */
	public void insertHitokoto(SQLiteDatabase db, String inputMsg){

		String sqlstr = " insert into Hitokoto (phrase) values(' " + inputMsg + " '); ";
			try {
				//トランザクション開始
				db.beginTransaction();
				db.execSQL(sqlstr);
				//トランザクション成功
				db.setTransactionSuccessful();
			} catch (SQLException e) {
				Log.e("ERROR" , e.toString());
			}finally {
				//トランザクション終了
				db.endTransaction();
			}
		return;
	}
	/**
	 *  引数のフレーズをHotokotoテーブルにインサートするプライベートメソッド
	 *  @param SQLiteDatabase インサート先のDBのインスタンス変数
	 *  @param inputMsgインサートするメッセージ
	 */
	public String selectRandomHitokoto(SQLiteDatabase db){

		String rtString = null;

		String sqlstr = "SELECT _id, Phrase FROM Hitokoto ORDER BY RANDOM(); ";
				try {
					//トランザクション開始
					SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sqlstr, null);
					if(cursor.getCount()!=0){
						//カーソル開始位置を先頭にする
						cursor.moveToFirst();
						rtString = cursor.getString(1);
					}
					cursor.close();

				} catch (SQLException e) {
						Log.e("ERROR", e.toString());
					}finally {
						// すでにカーソルもcloseしてあるので、なにもしない
					}
				return rtString;
	}

		/**
		 *  Hitokotoテーブルからデータをすべて取得
		 *  @param SQLiteDatabase SELECTアクセスするDBのインスタンス変数
		 *  @retun 取得したデータの塊の表(導出表)のレコードをポイントするカーソル
		 */
		public SQLiteCursor selectHitokotoList(SQLiteDatabase db){

			SQLiteCursor cursor = null;

			String sqlstr = " SELECT _id, phrase FROM Hitokoto ORDER BY _id; ";
			try {
			     // トランザクション開始
			     cursor = (SQLiteCursor)db.rawQuery(sqlstr, null);
			     if(cursor.getCount()!=0){
			    	 // カーソル開始位置を先頭にする
			    	 cursor.moveToFirst();
			     }
			     // cursorは呼び出し元へ返すからこおではcloseしない
			     // cursor.close();

			} catch (SQLException e) {
				Log.e("ERROR",e.toString());
			}finally{

			}
			return cursor;

		}
		/**
		 *  Hitokoto表から引数（id）で指定した値とカラム「_id」の値あが等しいレコードを削除
		 *  @param SQLiteDatabase DELETEアクセスするDBのインスタンス変数
		 *  @param id カラム「_id」と比較するために指定する削除条件の値
		 */
		public void deleteHitokoto(SQLiteDatabase db, int id){

			String sqlstr = "DELETE FROM Hitokoto where _id = " + id + ";";
			try {
				//トランザクション開始
				db.beginTransaction();
				db.execSQL(sqlstr);
				//トランザクション成功
				db.setTransactionSuccessful();
			} catch (SQLException e) {
				Log.e("ERROR" , e.toString());
			}finally {
				//トランザクション終了
				db.endTransaction();
			}
		}
	}




