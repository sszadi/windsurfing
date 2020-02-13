package com.sonalake.windsurfing.spot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class Spot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long spotId;
	private String name;
	private double latitude;
	private double longitude;
}
