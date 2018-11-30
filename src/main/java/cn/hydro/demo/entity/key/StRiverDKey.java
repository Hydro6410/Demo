package cn.hydro.demo.entity.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class StRiverDKey  implements Serializable {

    @Column(name = "STCD")
    private String stcd;

    @Column(name = "TM")
    private Date tm;
    // 省略setter,getter方法


    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public Date getTm() {
        return tm;
    }

    public void setTm(Date tm) {
        this.tm = tm;
    }

    @Override
    public String toString() {
        return "StRiverDKey{" +
                "stcd='" + stcd + '\'' +
                ", tm=" + tm +
                '}';
    }
}