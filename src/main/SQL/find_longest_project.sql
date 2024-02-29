-- 5 find_longest_project.sql;

with longest_project as (
select ROW_NUMBER() OVER (ORDER BY monthCount desc) R, name, p.monthCount
from (
	select c.name, p.id, p.client_id
	, EXTRACT(year FROM age(finish_date,start_date))*12 + EXTRACT(month FROM age(finish_date,start_date)) AS monthCount
	from project p
		left join client c on c.id=p.client_id
	)p
)
select 'Project '|| R ||' '|| name as name, monthCount
from longest_project
where R=1;