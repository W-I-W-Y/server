package wiwy.covid.apicall.webscaping;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebScraper {

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;
}
