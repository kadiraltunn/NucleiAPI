package com.example.demmooo.controller;

import com.example.demmooo.dto.ResponseDTO;
import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.dto.ResultWithScanDTO;
import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.exception.NucleiException;
import com.example.demmooo.model.ScanEntity;
import com.example.demmooo.service.ScanService;
import com.example.demmooo.service.ListResultService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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


    //TARAMA BAŞLATMA
    @PostMapping("/create-nuclei")
    @ResponseBody
    public ResponseDTO createNuclei(@RequestBody ScanDTO scanDTO) throws IOException, InterruptedException {
        scanService.createScan(scanDTO);
        return new ResponseDTO(true);
    }

    //TÜM TARAMALAR
    @GetMapping("/scans")
    public List<ScanDTO> getAllScans(){
        return scanService.getAllScans();
    }


    //TÜM TARAMALAR VE SONUÇLARI
    @GetMapping("scans-with-results")
    public List<ResultWithScanDTO> getAllResultsWithAllScans(){
        return resultService.getAllResultsWithAllScansById();
    }


    //"X" NUMARALI TARAMA VE SONUÇLARI
    @GetMapping("/results/{scanId}")
    public ResultWithScanDTO getResultsWithScan(@PathVariable Long scanId) {
        ScanEntity scanEntity = scanService.getOneScan(scanId);
        if (scanEntity == null)
            throw new NucleiException();
        return resultService.getAllResultsByScanId(scanId);
    }

    //TÜM SONUÇLAR
    @GetMapping("/results")
    public List<ResultDTO> getAllResults(){
        return resultService.getAllResults();
    }


    @ExceptionHandler(NucleiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleNuclei(){

    }
    
}
