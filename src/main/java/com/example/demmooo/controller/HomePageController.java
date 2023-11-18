package com.example.demmooo.controller;

import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.dto.ScanWithResultsDTO;
import com.example.demmooo.service.ResultService;
import com.example.demmooo.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping({"/"})
public class HomePageController {
    @Autowired
    private ScanService scanService;

    @Autowired
    private ResultService resultService;

    @GetMapping
    public String gotoHomePage(Model model) {
        ScanDTO scanDTO = new ScanDTO();
        model.addAttribute("scanDTO", scanDTO);
        return "index";
    }

    @PostMapping("/create-nuclei")
    public String createNuclei(@ModelAttribute("scanDTO") ScanDTO scanDTO, Model model)
            throws IOException, InterruptedException {
        //model.addAttribute("scanResults", scanService.startScanAndCreateScanEntity(scanDTO));
        model.addAttribute("id", scanService.startScanAndCreateScanEntity(scanDTO));
        return "select-scan";
    }

    @GetMapping("/scans")
    public String getScansPage() {
        return "scans";
    }

    @GetMapping("/scansRest")
    @ResponseBody
    public List<ScanDTO> getAllScans() {
        return scanService.getAllScans();
    }

    @GetMapping("/results")
    public String getAllResults(Model model) {
        model.addAttribute("results", resultService.getAllResults());
        return "results";
    }

    @GetMapping("/resultsRest")
    @ResponseBody
    public List<ResultDTO> getAllResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/scan/{id}")
    public String getOneScanPage(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "select-scan";
    }

    @GetMapping("/scan-result/{id}")
    @ResponseBody
    public ScanWithResultsDTO getOneScan(@PathVariable Long id) {
        return resultService.getScanWithResultsByScanId(id);
    }
}
