-- 6 find_youngest_eldest_workers.sql;

select t.type, w.name, w.birthday
from worker w
	inner join (
				select 'YOUNGEST' as type, max(birthday) birthday from worker
				union
				select 'ELDEST' as type, min(birthday) birthday from worker
				)t on t.birthday = w.birthday;
