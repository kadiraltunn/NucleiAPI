package com.example.demmooo.service;

import com.example.demmooo.dto.ScanDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class NucleiMainService {

    public void nucleiMain(ScanDTO scanDTO) throws IOException, InterruptedException {
        String target = scanDTO.getTarget();
        NucleiScan nucleiScan = new NucleiScan();
        JsonPartition jsonPartition = new JsonPartition();
        //nucleiScan.executeCommand(target);
    }
}
