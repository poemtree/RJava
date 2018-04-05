package r;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RTest {

	public static void main(String[] args) {
		RConnection rconn = null;
		try {
			rconn = new RConnection();
		} catch (RserveException e) {
			System.out.println("R Connection Error");
		}
		System.out.println("R Connection OK");
		try {
			rconn.setStringEncoding("utf8");
			rconn.eval("source('C:/rproject/day09/r1.R',encoding='UTF-8')");
			RList list = rconn.eval("r3()").asList();
			String[] time = list.at("time").asStrings();
			double[] line2 = list.at("line2").asDoubles();
			double[] line3 = list.at("line3").asDoubles();
			double[] line4 = list.at("line4").asDoubles();
						
			System.out.printf("\t%s\t%s\t%s\t%s\n",list.keyAt(0),list.keyAt(1),list.keyAt(2),list.keyAt(3));
			for(int i = 0; i < time.length; i++) {
				System.out.print(i+1);
				System.out.printf("\t%s\t%.0f\t%.0f\t%.0f\n",time[i],line2[i],line3[i],line4[i]);
			}
			
			// [{name:"data",datas:[1,2,3,4,5,....10]}]
			/*
			double[] results = rconn.eval("r2()").asDoubles();
			JSONArray ja = new JSONArray();
			JSONArray datas = new JSONArray();
			JSONObject jo = new JSONObject();
			for(int i = 0; i < results.length; i++)
				datas.add(results[i]);
			jo.put("name", "data");
			jo.put("datas", datas);
			ja.add(jo);
			System.out.println(ja);
			*/
			
			JSONArray ja = new JSONArray();
			JSONObject jo = null;
			JSONArray datas = null;
			for(int i = 0; i < list.size(); i++) {
				jo = new JSONObject();
				datas = new JSONArray();
				if(list.at(i).isFactor()) {
					for(int j = 0; j < list.at(i).length(); j++) 
						datas.add((list.at(i).asStrings())[j]);
				} else {
					for(int j = 0; j < list.at(i).length(); j++) 
						datas.add(Math.round((list.at(i).asDoubles())[j]));
				}
				jo.put(list.keyAt(i), datas);
				ja.add(jo);
			}
			System.out.println(ja);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
