package com.sonalake.windsurfing.weather;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpotRepository extends JpaRepository<Spot, Long> {
}
