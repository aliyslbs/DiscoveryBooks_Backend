package com.Raf.discoveryBooks.services;

import com.Raf.discoveryBooks.Dto.WriterDto;
import com.Raf.discoveryBooks.core.utilities.result.ErrorResult;
import com.Raf.discoveryBooks.core.utilities.result.Result;
import com.Raf.discoveryBooks.core.utilities.result.SuccessResult;
import com.Raf.discoveryBooks.models.FileEntity;
import com.Raf.discoveryBooks.models.Writer;
import com.Raf.discoveryBooks.repositorys.FileRespository;
import com.Raf.discoveryBooks.repositorys.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WriterService {

    private final WriterRepository writerRepository;
    private final FileRespository fileRespository;

    //save new writer
    public Result addWriter(WriterDto writerDto) {
        try {
            String filePath = "/images/writers/" + writerDto.getImage().getOriginalFilename();
            FileEntity fileEntity = FileEntity.builder()
                    .name(writerDto.getImage().getOriginalFilename())
                    .type(writerDto.getImage().getContentType())
                    .filePath(filePath)
                    .build();
            this.fileRespository.save(fileEntity);
            writerDto.getImage().transferTo(new File(System.getProperty("user.dir") + filePath));
            Writer newWriter = Writer.builder()
                    .name(writerDto.getName())
                    .image(fileEntity)
                    .build();
            writerRepository.save(newWriter);
            return new SuccessResult("writer saved successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ErrorResult("writer could not saved for some reoson");
        }
    }

    //update writer
    public Result updateWriter(WriterDto writerDto) {
        try {
            Writer writer = this.writerRepository.findById(writerDto.getId()).orElseThrow(() -> new RuntimeException("writer not found"));
            writer.setName(writerDto.getName());
            if (writerDto.getImage().isEmpty()) {
                if (writer.getImage() != null) {
                    FileEntity fileEntity = this.fileRespository.findByName(writer.getImage().getName()).orElseThrow(() -> new RuntimeException("image did not found"));
                    writer.setImage(fileEntity);
                }
            } else {
                String filePath = "/images/writers/" + writerDto.getImage().getOriginalFilename();
                FileEntity fileEntity = FileEntity.builder()
                        .name(writerDto.getImage().getOriginalFilename())
                        .type(writerDto.getImage().getContentType())
                        .filePath(filePath)
                        .build();
                this.fileRespository.save(fileEntity);
                writerDto.getImage().transferTo(new File(System.getProperty("user.dir") + filePath));
                writer.setImage(fileEntity);
            }
            this.writerRepository.save(writer);
            return new SuccessResult("writer updated successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ErrorResult("writer could not updated for some reoson");
        }
    }

    //delete writer by id
    public Result deleteWriter(long writerId) {
        try {
            this.writerRepository.deleteById(writerId);
            return new SuccessResult("writer deleted successfully");
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return new ErrorResult("writer could not updated for some reoson");
        }
    }

    public List<Writer> getAll() {
        return this.writerRepository.findAll();
    }

    public Writer findByName(String name) {
        return this.writerRepository.findByName(name).orElseThrow(() -> new RuntimeException("writer not found"));
    }

    //get writer image
    public byte[] getWriterImage(long imageId) throws java.io.IOException {
        String imageUrl = this.fileRespository.findById(imageId).get().getFilePath();
        return Files.readAllBytes(new File(System.getProperty("user.dir") + imageUrl).toPath());
    }
}
