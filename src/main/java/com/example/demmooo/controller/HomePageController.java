package com.example.demmooo.controller;

import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.service.ResultService;
import com.example.demmooo.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        model.addAttribute("scanResults", scanService.startScanAndCreateScanEntity(scanDTO));
        return "scan-result";
    }

    @GetMapping("/scans")
    public String getAllScans(Model model) {
        model.addAttribute("scans", scanService.getAllScans());
        return "scans";
    }

    @GetMapping("/results")
    public String getAllResults(Model model) {
        model.addAttribute("results", resultService.getAllResults());
        return "results";
    }

    @GetMapping("/scan/{id}")
    public String getOneScan(@PathVariable Long id, Model model) {
        model.addAttribute("scanResults", resultService.getScanWithResultsByScanId(id));
        return "select-scan";
    }
}
