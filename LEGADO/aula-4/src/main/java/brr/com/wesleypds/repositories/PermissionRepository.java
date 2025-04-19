package brr.com.wesleypds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import brr.com.wesleypds.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
