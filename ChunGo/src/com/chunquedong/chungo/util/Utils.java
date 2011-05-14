package com.chunquedong.chungo.util;

import com.chunquedong.chungo.Board;

public class Utils {
	//�������̵���
	public static Coordinate[] createStar(){
		Coordinate[] cs=new Coordinate[9];
		
		int dao3=Board.n-4;
		cs[0]=new Coordinate(3,3);
		cs[1]=new Coordinate(dao3,3);
		cs[2]=new Coordinate(3,dao3);
		cs[3]=new Coordinate(dao3,dao3);
		
		int zhong=Board.n/2;
		
		cs[4]=new Coordinate(3,zhong);
		cs[5]=new Coordinate(zhong,3);
		cs[6]=new Coordinate(zhong,dao3);
		cs[7]=new Coordinate(dao3,zhong);
		
		cs[8]=new Coordinate(zhong,zhong);
		
		return cs;
	}
	
	// ��ת��ɫ
	public static int getReBW(int bw) {
		if (bw == Board.White)
			return Board.Black;
		if (bw == Board.Black)
			return Board.White;

		throw new ChunGoException("ilegateState");
	}
}
