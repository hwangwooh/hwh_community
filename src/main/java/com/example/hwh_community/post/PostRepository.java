package com.example.hwh_community.post;

import com.example.hwh_community.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post,Long>,PostRepositoryCustom {
    @Query("select e from Post e ORDER BY e.id DESC")
    Page<Post> findByTitleContainingOrContentContaining(String searchText, String searchText1, Pageable pageable);


}
