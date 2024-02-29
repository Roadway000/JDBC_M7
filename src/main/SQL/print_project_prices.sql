-- 7 print_project_prices.sql

select 'Project ' || T.id as name, monthCount * summa as price
from (
	select p.id, p.start_date, p.finish_date
		, EXTRACT(year FROM age(finish_date,start_date))*12 + EXTRACT(month FROM age(finish_date,start_date)) as monthCount
		, COALESCE(pws.summa,0) as summa
	from project p
		left join (
			select pw.project_id, sum(w.salary) as summa
			from project_worker pw
				left join worker w on w.id=pw.worker_id
			group by pw.project_id
		) pws on pws.project_id = p.id
	)T
order by 2 desc;
