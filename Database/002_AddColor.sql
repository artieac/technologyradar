ALTER TABLE RadarCategories ADD Color nvarchar(8);

UPDATE RadarCategories SET Color = "#8FA227" where Id = 0;
UPDATE RadarCategories SET Color = "#587486" where Id = 1;
UPDATE RadarCategories SET Color = "#DC6F1D" where Id = 2;
UPDATE RadarCategories SET Color = "#B70062" where Id = 3;