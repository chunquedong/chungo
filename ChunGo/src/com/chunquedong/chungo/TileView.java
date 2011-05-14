package com.chunquedong.chungo;

import com.chunquedong.chungo.util.Coordinate;
import com.chunquedong.chungo.util.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TileView extends View {

	private Board board;
	private Paint paint;
	private double xOffset;
	private double yOffset;
	private double tileSize;

	public TileView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.paint = new Paint();
		this.xOffset = 0.5;
		this.yOffset = 0.5;
		this.tileSize = 1;
		this.board = new Board();
		
		Log.d(ChunGo.Tag, "newTileView");
	}

	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board){
		this.board=board;
		this.invalidate();
	}

	//------------------------------------------------------------------��ͼ
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//������
		drawLineGrid(canvas);
		drawStar(canvas);
		
		//������
		drawPiece(canvas);
		drawFlag(canvas);
	}

	//������
	private void drawPiece(Canvas canvas) {
		for (int x = 0; x < Board.n; x += 1) {
			for (int y = 0; y < Board.n; y += 1) {
				int bw = board.getValue(x, y);
				if (bw != Board.None) {
					if (bw == Board.Black)
						paint.setColor(Color.BLACK);
					else
						paint.setColor(Color.WHITE);

					canvas.drawCircle(x2Screen(x), y2Screen(y),
							(float) (tileSize / 2d), paint);
				}
			}
		}
	}

	//���������λ�õı��
	private void drawFlag(Canvas canvas) {
		paint.setColor(Color.RED);
		Coordinate c=board.getLastPosition();
		if(c!=null){
			canvas.drawRect(x2Screen(c.x)-3, y2Screen(c.y)-3
				, x2Screen(c.x)+3f, y2Screen(c.y)+3f, paint);
		}
	}
	
	//�����̵���
	private void drawStar(Canvas canvas) {
		paint.setColor(Color.BLUE);
		
		for(Coordinate c:Utils.createStar()){
			if(c!=null){
				canvas.drawCircle(x2Screen(c.x), y2Screen(c.y),
						3f, paint);
			}
		}
	}

	//������������
	private void drawLineGrid(Canvas canvas) {
		paint.setColor(Color.BLUE);
		for (int i = 0; i < Board.n; i++) {
			drawVLine(canvas, i);
			drawHLine(canvas, i);
		}
	}

	//��ֱ��
	private void drawVLine(Canvas canvas, int i) {
		canvas.drawLine(x2Screen(i), y2Screen(0), x2Screen(i),
				y2Screen(Board.n - 1), paint);
	}

	//ˮƽ��
	private void drawHLine(Canvas canvas, int i) {
		canvas.drawLine(x2Screen(0), y2Screen(i), x2Screen(Board.n - 1),
				y2Screen(i), paint);
	}

	//------------------------------------------------------------------����任
	
	private float x2Screen(int x) {
		return (float) (x * tileSize + xOffset);
	}

	private float y2Screen(int y) {
		return (float) (y * tileSize + yOffset);
	}

	private int x2Coordinate(float x) {
		return (int) Math.round((x - xOffset) / tileSize);
	}

	private int y2Coordinate(float y) {
		return (int) Math.round((y - yOffset) / tileSize);
	}

	//------------------------------------------------------------------�¼�
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		int wh = Math.min(w, h);
		tileSize = wh / (double) Board.n;

		xOffset = tileSize / 2;
		yOffset = tileSize / 2;

		super.onSizeChanged(w, wh, oldw, oldh);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {

			float xf = event.getX();
			float yf = event.getY();

			int x = x2Coordinate(xf);
			int y = y2Coordinate(yf);

			if (board.put(x, y)) {
				this.invalidate();
				return true;
			}
		}
		return true;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int m=Math.max(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(m, m);
	}
}
