package io.jpom.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {

	private Integer UserID;
	private String UserName;
	private String Power;
	private String Detail;

}
