package com.spring.jpa.chap04_relation.repository;

import com.spring.jpa.chap04_relation.entity.Department;
import com.spring.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class DepartmentRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EntityManager entityManager;

    
    @Test
    @DisplayName("특정 부서를 조회하면 해당 부서원들도 함께 조회되어야 한다")
    void testFindDept() {
        //given
        long id = 2L;
        //when
        Department department = departmentRepository.findById(id)
                .orElseThrow();

        //then
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        
        System.out.println("department = " + department);
        System.out.println("department.getEmployees() = " + department.getEmployees());
        
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Test
    @DisplayName("Lazy 로딩과 Eager 로딩의 차이")
    void testLazyAndEager() {
        //3번 사원을 조회하고 싶은데, 부서정보는 필요없다.
        //given
        long id = 3L;
        //when
        Employee employee = employeeRepository.findById(id)
                .orElseThrow();
        //then
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("employee = " + employee);

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
    
    @Test
    @DisplayName("양방향 연관관계에서 연관데이터 수정")
    void testChangeDept() {
        //3번 사원의 부서를 2번부서에서 1번부서로 변경하도록 한다.
        //given
        long id = 3L;

        Employee foundEmp = employeeRepository.findById(id)
                .orElseThrow();

        Department newDept = departmentRepository.findById(1L).orElseThrow();

        // 사원의 부서정보를 ㅓㅇ벧이트 하면서, 부서에 대한 정보도 가팅 업데이트
        foundEmp.setDepartment(newDept);

        employeeRepository.save(foundEmp);

        // 변경 감지 (더티 체크) 후 변경된 내용을 DB에 반영하는 역할을 합니다.
//        entityManager.flush();
//        entityManager.clear();

        //then
        Department fountDept = departmentRepository.findById(1L).orElseThrow();

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");

        fountDept.getEmployees().forEach(System.out::println);

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
    @Test
    @DisplayName("N+1 문제 발생예시")
    void textNPlus1Ex() {
        //given
        List<Department> departments = departmentRepository.findAll();
        //when
        departments.forEach(dept -> {
            List<Employee> employees = dept.getEmployees();

            System.out.println("============ 사원리스트 ============");
            System.out.println("employees = " + employees);
            System.out.println("\n\n");

        });
        //then
    }
    @Test
    @DisplayName("N+1 문제 해결예시")
    void textNPlus1Solution() {
        //given
        List<Department> departments = departmentRepository.findAllIncludeEmployees();
        //when
        departments.forEach(dept -> {
            List<Employee> employees = dept.getEmployees();

            System.out.println("============ 사원리스트 ============");
            System.out.println("employees = " + employees);
            System.out.println("\n\n");

        });
        //then
    }
}