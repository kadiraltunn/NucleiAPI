package com.example.demmooo.controller;

import com.example.demmooo.dto.ResponseDTO;
import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.model.ResultEntity;
import com.example.demmooo.service.CreateNucleiService;
import com.example.demmooo.service.JsonPartition;
import com.example.demmooo.service.ListResultService;
import com.example.demmooo.service.NucleiMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/nuclei")
public class NucleiController
{
    @Autowired
    private ListResultService resultService;

    @Autowired
    private CreateNucleiService nucleiService;

    @Autowired
    private NucleiMainService nucleiMainService;

    @Autowired
    private JsonPartition jsonPartition;

    @PostMapping("/create-nuclei")
    @ResponseBody
    public ResponseDTO createNuclei(@RequestBody ScanDTO scanDTO) throws IOException, InterruptedException {
        nucleiMainService.nucleiMain(scanDTO);
        nucleiService.createScan(scanDTO);
        jsonPartition.JsonPartition(scanDTO);
        ResponseDTO res = new ResponseDTO(true);
        return res;
    }

    @GetMapping(value = "/{id}")
    private Object getResultById(@PathVariable("id") Long id){

        return resultService.resultList(id);
    }
}
