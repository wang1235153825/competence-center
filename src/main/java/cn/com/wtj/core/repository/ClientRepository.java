package cn.com.wtj.core.repository;

import cn.com.wtj.core.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 2019/9/4.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Repository
public interface ClientRepository extends JpaRepository<Client,String> {

    Optional<Client> findByClientId(String clientId);

}
