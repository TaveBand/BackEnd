package ys_band.develop.dto.jaehyun;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ys_band.develop.domain.File;


@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileDTO {
    private String file_url;
    private String storedFileNmae;
    private String OriginalFileName;

    public File toEntity(){
        return File.builder()
                .originalFileName(this.OriginalFileName)
                .file_url(this.file_url)
                .build();
    }
}