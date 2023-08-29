package com.orkes.assignment.spreadsheet.sheet.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.orkes.assignment.spreadsheet.sheet.services.ISheetService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SheetController.class)
public class SheetControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private ISheetService sheetService;

    @Test
    @DisplayName("GET /api/sheet/ping Success")
    void testPing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/sheet/ping"))
                .andExpect(status().isOk());
    }

    @Test
    void testSetCellValueIntegerSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/sheet/A1")
                .content("43").contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
    }

    @Test
    void testSetCellValueExpressionSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/sheet/A2")
                .content("=A1+10").contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
    }

    @Test
    void testGetCellValueSuccess() throws Exception {
        when(sheetService.getCellValue("A1")).thenReturn(20);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/sheet/A1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("20"));
    }

}
