-- 4 find_max_projects_client.sql;

with max_projectCount as (
	select client_id, projectCount, row_number() over(order by projectCount desc) as R
	from (
		select p.client_id, count(*) projectCount
		from project p
		group by p.client_id
		)T1
)
select c."name" , projectCount
from max_projectCount m
	left join client c on c.id = client_id
where R=1;