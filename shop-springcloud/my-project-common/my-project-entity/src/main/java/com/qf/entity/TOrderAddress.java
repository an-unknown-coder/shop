package com.qf.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_order_address")
public class TOrderAddress  implements Serializable {

	private Integer aid;

	@Column(name = "address_name")
	private String addressName;

	@Column(name = "address_phone")
	private String addressPhone;

	@Column(name = "address_sheng")
	private String addressSheng;

	@Column(name = "address_shi")
	private String addressShi;

	@Column(name = "address_qu")
	private String addressQu;

	@Column(name = "address_info")
	private String addressInfo;

	@Column(name = "u_id")
	private Integer uId;

}
