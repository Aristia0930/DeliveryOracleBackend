package org.example.backend.gemini;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GeminiMapper {
    List<String> order(@Param("id") int id);

    List<String> menuList();
}
