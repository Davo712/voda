package com.company.voda.controller;


import com.company.voda.exceptions.ExistsException;
import com.company.voda.exceptions.NullException;
import com.company.voda.model.Image;
import com.company.voda.repository.ImageRepository;
import com.company.voda.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class GalleryController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;


    @RequestMapping(value = "/gallery", method = RequestMethod.GET)
    public String gallery(Model model) throws IOException {


        HashMap<String, String> hashMap = imageService.getImages();

        model.addAttribute("hashMap", hashMap);


        return "gallery";
    }

    @RequestMapping(value = "/hello/addImage", method = RequestMethod.POST)
    public String addImage(@RequestParam("f") MultipartFile f, Model model) {


        String redirect = null;

        try {
            redirect = imageService.addImage(f);
            return redirect;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExistsException e) {
            model.addAttribute("message", "Image name exsist");
            return "hello";
        } catch (NullException e) {
            return "hello";
        }

        return redirect;


    }


    @RequestMapping("/gallery/{link}")
    public String getImage(@PathVariable() String link, Model model) throws IOException {

        Image image = imageRepository.getByLink(link);

        if (image == null) {
            return "hello";
        }

        String encodedString = imageService.getEncode(new File("C:\\Users\\dav\\Desktop\\Images\\" + image.getName()));
        model.addAttribute("image", encodedString);


        return "image";
    }


}
