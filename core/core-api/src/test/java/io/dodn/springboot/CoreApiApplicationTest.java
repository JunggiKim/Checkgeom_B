package io.dodn.springboot;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CoreApiApplicationTest extends ContextTest {

	public void solution(String[] want, int[] number, String[] discount) {

		Map<String, Integer> wantMap = new HashMap<>();
		for (int j = 0; j < want.length; j++) {
			wantMap.put(want[j], number[j]);
		}
		int answer = 0;
		int foreachCheckNumber = 10;

		for (int i = 0; i < discount.length; i++) {

			Map<String,Integer> executeWantMap = new HashMap<>();
			executeWantMap.putAll(wantMap);
			int foreachNumber = 0;
			for (int j = i; j < discount.length; j++) {

				foreachNumber++;
				String item = discount[j];

				Integer mapItem = executeWantMap.get(item);
				if(mapItem == null){
					break;
				}
				mapItem--;
				System.out.println("넣는 값 = "+ item + "체크후 개수 = " + mapItem);
				executeWantMap.put(item , mapItem);
				if(foreachNumber == foreachCheckNumber){
					boolean match = executeWantMap.values().stream().allMatch(integer -> integer == 0);
					System.out.println("맵안의 값  = " +  executeWantMap.toString());
					if(match) {
						answer++;
					}
					break;
				}

			}

		}

		System.out.println(answer);
	}

}
