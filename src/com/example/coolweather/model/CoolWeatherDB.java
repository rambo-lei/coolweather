package com.example.coolweather.model;

import java.util.ArrayList;
import java.util.List;

import com.example.coolweather.db.CoolWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.MovementMethod;

public class CoolWeatherDB {
	
	/*���ݿ���*/
	
	public static final String DB_NAME = "cool_weather";
	
	/*���ݿ�汾*/
	
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/*�����췽��˽�л�*/
	
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/*��ȡCoolWeatherDB��ʵ��*/
	
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/*��province���ݴ洢�����ݿ�*/
	
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
			
		}
	}
	
	/*�����ݿ��ȡȫ�����е�ʡ����Ϣ*/
	
	public List<Province> loadProvince(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("provinceCode")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
	
	/*��cityʵ���洢�����ݿ�*/
	
	public void saveCity(City city){
		if(city != null){
			ContentValues values  = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id",city.getProvinceId());
			db.insert("City", null, values);
		}
		
		/*�����ݿ���ȡ��city�е�����*/
		
	}
	
	public List<City> loadCities(int province_id){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("city", null, "province_id = ?", new String[]{String.valueOf(province_id)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(province_id);
				list.add(city);
				
			}while(cursor.moveToNext());
			
		}
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
	
	
	/*��county�е����ݴ��뵽���ݿ�*/
	
	public void  saveCounty(County county){
		if(county != null){
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id",county.getCityId());
			db.insert("County", null, values);
		}
	}
	
	/*���ؼ����ݴ����ݿ���ȡ��*/
	
	public List<County> loadCounties(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf("City_id")}, null, null, null);
		if(cursor.moveToFirst()){
			do{
			County county = new County();
			county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
			county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			county.setCityId(cityId);
			county.setId(cursor.getInt(cursor.getColumnIndex("id")));
			}while(cursor.moveToNext());
		}
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
	
	
	

}
