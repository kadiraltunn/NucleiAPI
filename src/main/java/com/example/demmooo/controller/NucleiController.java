package com.example.demmooo.controller;

import com.example.demmooo.dto.ResponseDTO;
import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.dto.ScanWithResultsDTO;
import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.exception.NucleiException;
import com.example.demmooo.model.ScanEntity;
import com.example.demmooo.service.ResultService;
import com.example.demmooo.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/nuclei")
public class NucleiController {
    @Autowired
    private ScanService scanService;

    @Autowired
    private ResultService resultService;

    //TÜM TARAMALAR
    @GetMapping("/scans")
    public List<ScanDTO> getAllScans() {
        return scanService.getAllScans();
    }

    //TÜM SONUÇLAR
    @GetMapping("/results")
    public List<ResultDTO> getAllResults() {
        return resultService.getAllResults();
    }

    //TÜM TARAMALAR VE SONUÇLARI
    @GetMapping("scans-with-results")
    public List<ScanWithResultsDTO> getAllScansWithAllResults() {
        return resultService.getAllScanWithResults();
    }


    //ID-TARAMA VE SONUÇLARI
    @GetMapping("/scans/{scanId}")
    public ScanWithResultsDTO getScanWithResults(@PathVariable Long scanId) {
        ScanEntity scanEntity = scanService.getOneScanById(scanId);
        if (scanEntity == null)
            throw new NucleiException();
        return resultService.getScanWithResultsByScanId(scanId);
    }

    //TARAMA SİLME
    @DeleteMapping("/scans/{scanId}")
    public ResponseDTO deleteOneScan(@PathVariable Long scanId) {
        scanService.deleteOneScan(scanId);
        return new ResponseDTO(true);
    }

    @ExceptionHandler(NucleiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleNuclei() {

    }

}
