package com.company.voda.service.impl;

import com.company.voda.exceptions.ExistsException;
import com.company.voda.exceptions.NullException;
import com.company.voda.model.Image;
import com.company.voda.repository.ImageRepository;
import com.company.voda.service.ImageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;


    @Override
    public HashMap<String,String> getImages() throws IOException {


        String imagePath = "C:/Users/dav/Desktop/Images/";
        List<Image> list = imageRepository.findAll();

        HashMap<String,String> hashMap = new HashMap<>();

        for (Image image : list) {


            hashMap.put(image.getLink(),getEncode(new File(imagePath + image.getName())));
        }

        return hashMap;



    }

    @Override
    public String addImage(MultipartFile f) throws IOException, ExistsException, NullException {


        byte[] arrayImage = f.getBytes();

        if (f.getOriginalFilename().equals("")) {
            throw new NullException();
        }

        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = false;
        String link = RandomStringUtils.random(length, useLetters, useNumbers);


        if (imageRepository.existsByName(f.getOriginalFilename())) {
            throw new ExistsException();
        }

        Image image = new Image();
        image.setName(f.getOriginalFilename());
        image.setLink(link);
        imageRepository.save(image);


        String pathsourceName = "C:\\Users\\dav\\Desktop\\Images";
        File file = new File(pathsourceName + "\\" + image.getName());

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(arrayImage);
        fileOutputStream.close();


        String redirect = "redirect:/gallery/" + image.getLink();

        return redirect;
    }

    public String getEncode(File file) throws IOException {

           String encodedString = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));

           return encodedString;

    }

}
