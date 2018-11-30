package cn.hydro.demo.controller;


import cn.hydro.demo.entity.StRiverR;
import cn.hydro.demo.repository.StRiverRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("demo/")
public class DemoController {

    @Autowired
    private StRiverRRepository stRiverRRepository;

    @GetMapping("/getData")
    public List<StRiverR> getData(@RequestParam String stcd,
                                  @RequestParam Date startTime,
                                  @RequestParam Date endTime) {



        List<StRiverR> list = stRiverRRepository.findByStcdAndTmBetween(stcd, startTime, endTime);
//        List<StRiverR> list = stRiverRRepository.findAll();

        return list;
    }

}
