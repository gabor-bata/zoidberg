# Example for getting weather forecast for tomorrow using Yahoo Weather Java API
locations = ['London, United Kingdom', 'Szeged, Hungary']
locations.map do |location|
  channel = @weather_service.get_forecast_for_location(location, DegreeUnit::CELSIUS).first(1)[0]
  channel.item.forecasts.drop(1).take(1).map do |forecast|
    {
      location: "#{channel.location.city}, #{channel.location.country}",
      day: forecast.date.to_s,
      temperature: "#{forecast.low} / #{forecast.high} #{channel.units.temperature}",
      description: forecast.text
    }
  end
end
