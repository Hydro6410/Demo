package cn.hydro.demo.repository;


import cn.hydro.demo.entity.StRiverR;
import cn.hydro.demo.entity.key.StRiverDKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface StRiverRRepository extends JpaRepository<StRiverR, StRiverDKey> {

    @Query(value = "select * from st_river_r where stcd=?1 and tm between ?2 and ?3", nativeQuery = true)
    public List findByStcdAndTmBetween(String stcd, Date startTm, Date endTm);


//    public List findStRiverRSByTmBetween(Date startTm, Date endTm);
}
