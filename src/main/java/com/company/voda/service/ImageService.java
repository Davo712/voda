package com.company.voda.service;

import com.company.voda.exceptions.ExistsException;
import com.company.voda.exceptions.NullException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public interface ImageService {

    HashMap<String,String> getImages() throws IOException;

    String addImage(MultipartFile f) throws IOException, ExistsException, NullException;

    String getEncode(File file) throws IOException;
}
