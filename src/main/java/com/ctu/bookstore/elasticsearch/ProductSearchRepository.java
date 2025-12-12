// package com.ctu.bookstore.elasticsearch;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

// import java.util.List;

// public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {
//     List<ProductDocument> findByNameProductContainingIgnoreCase(String keyword);
//     Page<ProductDocument> findByNameProductContainingIgnoreCase(String keyword, Pageable pageable);
// }
