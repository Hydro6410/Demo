package cn.hydro.demo.entity;


import cn.hydro.demo.entity.key.StRiverDKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ST_RIVER_R")
public class StRiverR {


    @EmbeddedId
    private StRiverDKey id;

//    private String stcd;
//    private Date tm;
    @Column(name = "Z")
    private Double z;
    @Column(name = "Q")
    private Double q;
    @Column(name = "DOX")
    private Double dox;
    @Column(name = "CODMN")
    private Double codmn;
    @Column(name = "NH3H")
    private Double nh3h;

    public StRiverDKey getId() {
        return id;
    }

    public void setId(StRiverDKey id) {
        this.id = id;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double getQ() {
        return q;
    }

    public void setQ(Double q) {
        this.q = q;
    }

    public Double getDox() {
        return dox;
    }

    public void setDox(Double dox) {
        this.dox = dox;
    }

    public Double getCodmn() {
        return codmn;
    }

    public void setCodmn(Double codmn) {
        this.codmn = codmn;
    }

    public Double getNh3h() {
        return nh3h;
    }

    public void setNh3h(Double nh3h) {
        this.nh3h = nh3h;
    }
}
