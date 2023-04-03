package com.example.demmooo.controller;

import com.example.demmooo.dto.ResponseDTO;
import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.model.ResultEntity;
import com.example.demmooo.service.ScanService;
import com.example.demmooo.service.ListResultService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/nuclei")
public class NucleiController
{
    private ScanService scanService;

    private ListResultService resultService;

    public NucleiController(ScanService scanService, ListResultService resultService) {
        this.scanService = scanService;
        this.resultService = resultService;
    }

    @PostMapping("/create-nuclei")
    @ResponseBody
    public ResponseDTO createNuclei(@RequestBody ScanDTO scanDTO) throws IOException, InterruptedException {
        scanService.createScan(scanDTO);
        ResponseDTO res = new ResponseDTO(true);
        return res;
    }

    @GetMapping("/{scanId}")
    public Iterable<ResultDTO> getAllResults(@PathVariable Long scanId) {
        return resultService.getAllResultsByScanId(scanId);
    }
    
}
