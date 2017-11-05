var myRecord :
record
    rec1, rec2 : int;
    rec3 : boolean
end record;
begin
    bind var count to myRecord;
end;
var price, total_price : real;
total_price := 3.70 * (5 + 2) / 2.e5;
var count : int;
count := 0;
if 3.70 >= 2.95 then
  total_price := total_price + price;
elsif count = 2 then
    total_price := total_price + price;
elsif 3 = 2 and (4 mod 5) = 0 or 3 = 2 then
    total_price := total_price + price;
else
    bind var count to myRecord;
end if;
loop
  count := count +1; 
  total_price := total_price + price;
end loop;
loop
    price := 3.4;
    assert 4 not= 2;
    exit when count = 10;
end loop;
exit;
