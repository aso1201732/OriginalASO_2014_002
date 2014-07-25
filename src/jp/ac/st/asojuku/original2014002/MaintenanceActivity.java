package jp.ac.st.asojuku.original2014002;

import android.app.Activity;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * @author student
 *
 */
public class MaintenanceActivity extends Activity
	implements View.OnClickListener,AdapterView.OnItemClickListener{

	// SQLiteデータベース空間を操作するインスタンス変数を宣言
	SQLiteDatabase sdb = null;
	// MySQLiteOpenHelperを操作するインスタンス変数を宣言
	MySQLiteOpenHelper helper = null;

	//リストにて選択そたHitokotoテーブルのレコードの「_id」カラム値を保持する変数んの宣言
	int selectedID = -1;
	//リストにて選択した行番号を保持する変数の宣言
	int lastPosition = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintenanceactivty);
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();

		//各view部品を操作するidを取得
		Button btnDelete = (Button)findViewById(R.id.btnDELETE);
		Button btnMainte_Back = (Button)findViewById(R.id.btnMAINTE_BACK);
		ListView lstHitokoto = (ListView)findViewById(R.id.LvHITOKOTO);

		// 各ButtonにOnClickListenerをセット
		btnMainte_Back.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		// LIstViewにOnItemClickListenerをセット
		lstHitokoto.setOnItemClickListener(this);

		// ListViewにDBの値をセット
		this.setDBValuetoList(lstHitokoto);

	}


	/**
	 * 引数のListViewにDBのデータをセット
	 * @paramlstHitokoto 対象となるListView
	 */
	private void setDBValuetoList(ListView lstHitokoto){

		SQLiteCursor cursor = null;

		// クラスのフィールド変数がNULLなら、データベース空間オープン
		if(sdb == null) {
			helper = new MySQLiteOpenHelper(getApplicationContext());

		}
		try{
			sdb = helper.getWritableDatabase();
		}catch(SQLiteException e){
			// 異常終了
			Log.e("ERROR", e.toString());
		}
		// MySQLiteOpenHelperにSELECT文を実行させて結果のカーソルを受け取る
		cursor = this.helper.selectHitokotoList(sdb);

		// dblayout: ListViewにさらにレイアウトを指定するもの
		int db_layout = android.R.layout.simple_list_item_activated_1;
		// from: カーソルからListViewに指定するカラムに値を指定するもの
		String[]from = {"phrase"};
		// to = new int[]{android.R.id.text1};
		int[] to = new int[]{android.R.id.text1};

		// ListViewにセットするアダプターを生成
		// カーソルをもとに、fromの列から、toのViewへの値のマッピングが行われる。
		SimpleCursorAdapter adapter =
				new SimpleCursorAdapter(this,db_layout,cursor,from,to,0);

		// アダプターを設定します
		lstHitokoto.setAdapter(adapter);

	}

	/**
	 *  Hitokotoテーブルから、引数で指定し「_id」と同じ値を持つレコードを削除
	 *  @param id 指定する値
	 */
	private void deleteFromHitokoto(int id){
		// クラスのフィールド変数がNULLなら、データベース空間オープン
		if(sdb == null) {
			helper = new MySQLiteOpenHelper(getApplicationContext());
		}
		try{
			sdb = helper.getWritableDatabase();
		}catch(SQLiteException e){
			// 異常終了
			Log.e("ERROR", e.toString());
		}
		// MySQLiteOpenHelperにDELETE文を実行させる
		this.helper.deleteHitokoto(sdb, id);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO 自動生成されたメソッド・スタブ

		// 前に選択中の行があれば、背景色を透明にする
		if(this.selectedID!=-1){
			parent.getChildAt(this.lastPosition).setBackgroundColor(0);
		}
		// 選択中の行の背景色をグレーにする
		view.setBackgroundColor(android.graphics.Color.LTGRAY);

		// 選択行のレコードを指し示すカーソルを取得
		SQLiteCursor cursor = (SQLiteCursor)parent.getItemAtPosition(position);
		// カーソルのレコｂｎードから、「_id」の値を取得して記憶
		this.selectedID = cursor.getInt(cursor.getColumnIndex("_id"));
		// 何行目を選択したかも記憶
		this.lastPosition = position;

	}


	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ

		switch(v.getId()) { //どのボタンが押されたか班てお
		case R.id.btnMAINTE: // 削除ボタンが押された

			//選択行があれば
			if(this.selectedID != -1){
				this.deleteFromHitokoto(this.selectedID);
				ListView lstHitokoto = (ListView)findViewById(R.id.LvHITOKOTO);
				// ListViewにDBをセット
				this.setDBValuetoList(lstHitokoto);
				// 選択行を忘れる
				this.selectedID = -1;
				this.lastPosition = -1;
			}
			else{
				// なければ、トースト（簡易メッセージ）を表示
				 Toast.makeText(MaintenanceActivity.this, "削除する行を選んでください",Toast.LENGTH_SHORT).show();
			}
		break;
		case R.id.btnMAINTE_BACK: // 戻るボタンが押された
			// 今の画面Activityを消して、前の画面activityに戻る
			finish();
		break;
			}
		}
	}
