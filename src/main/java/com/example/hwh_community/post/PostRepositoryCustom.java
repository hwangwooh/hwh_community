package com.example.hwh_community.post;



import com.example.hwh_community.api.Dto.PostApiDto;
import com.example.hwh_community.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);


    List<Post> findnotice();

    List<Post> findbyidapi(Long id);




 }
