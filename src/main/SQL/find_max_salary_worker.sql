--3 find_max_salary_worker.sql;

select name, salary
from worker
where salary in (select max(salary) from worker)
order by salary desc;
